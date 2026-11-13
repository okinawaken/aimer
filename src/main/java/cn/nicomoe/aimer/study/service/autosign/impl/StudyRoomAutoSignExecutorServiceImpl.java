package cn.nicomoe.aimer.study.service.autosign.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.constant.StudyRoomCacheConstants;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosign.StudyRoomAutoSignFilterChain;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatQueryClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
import cn.nicomoe.aimer.study.model.event.StudyRoomBarkNotifyEvent;
import cn.nicomoe.aimer.study.service.autosign.StudyRoomAutoSignExecutorService;
import cn.nicomoe.aimer.study.service.client.StudyRoomSignService;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.RedisLockUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 自动签到执行器实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudyRoomAutoSignExecutorServiceImpl implements StudyRoomAutoSignExecutorService {

    private final StudyRoomSignService signService;
    private final StudyRoomAutoSignFilterChain filterChain;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void execute(Long userId) {
        String lockKey = StudyRoomCacheConstants.AUTO_SIGN_EXECUTE_LOCK + userId;
        if (!RedisLockUtils.tryLock(lockKey)) {
            log.error("自动签到正在执行中，加锁失败，忽略，userId={}", userId);
            return;
        }

        StudyRoomAutoSignContext context = new StudyRoomAutoSignContext(userId);

        try {
            FilterResultDTO<?> filterResultDTO = filterChain.doFilter(context);
            if (!filterResultDTO.isSuccess()) {
                log.info("自动签到执行被过滤，userId={}，context={}，过滤原因={}", userId, GsonUtils.toJson(context), filterResultDTO.getErrorMsg());
                return;
            }

            StudyRoomUserReserveClientRespDTO reserveRespDTO = context.getReserveClientRespDTO();
            StudyRoomUserPackageClientRespDTO userPackageRespDTO = context.getUserPackageClientRespDTO();

            StudyRoomSignSeatQueryClientReqDTO reqDTO = new StudyRoomSignSeatQueryClientReqDTO();
            reqDTO.setQrCode(StrUtil.EMPTY);
            reqDTO.setVenueId(reserveRespDTO.getVenueId());
            reqDTO.setSignInUserPackageId(userPackageRespDTO.getSignInUserPackageId());
            reqDTO.setSignAnotherFlag(0);
            signService.studySeatList(reqDTO, userId);
            String successBody = StrUtil.format("门店：{}\n座位：{}", reserveRespDTO.getVenueName(), reserveRespDTO.getSeatNumber());
            eventPublisher.publishEvent(new StudyRoomBarkNotifyEvent(userId, "自动签到成功", successBody));
        } catch (Exception e) {
            log.error("自动签到执行失败，userId={}，context={}", userId, GsonUtils.toJson(context), e);
            eventPublisher.publishEvent(new StudyRoomBarkNotifyEvent(userId, "自动签到失败", e.getMessage()));
        } finally {
            RedisLockUtils.unlock(lockKey);
        }
    }
}
