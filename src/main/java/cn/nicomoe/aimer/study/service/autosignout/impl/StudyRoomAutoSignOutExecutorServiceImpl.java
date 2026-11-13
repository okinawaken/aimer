package cn.nicomoe.aimer.study.service.autosignout.impl;

import cn.nicomoe.aimer.study.constant.StudyRoomCacheConstants;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosignout.StudyRoomAutoSignOutFilterChain;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignOutClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.autosignout.StudyRoomAutoSignOutExecutorService;
import cn.nicomoe.aimer.study.service.client.StudyRoomSignService;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.RedisLockUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 自动签退执行器实现。
 * <p>
 * 过滤器链负责判断「是否到期、签哪几条、用什么套餐和坐标」，执行器只负责逐条发起签退请求，
 * 单条失败不影响其余记录。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudyRoomAutoSignOutExecutorServiceImpl implements StudyRoomAutoSignOutExecutorService {

    private final StudyRoomSignService signService;
    private final StudyRoomAutoSignOutFilterChain filterChain;

    @Override
    public void execute(Long userId) {
        String lockKey = StudyRoomCacheConstants.AUTO_SIGN_OUT_EXECUTE_LOCK + userId;
        if (!RedisLockUtils.tryLock(lockKey)) {
            log.error("自动签退正在执行中，加锁失败，忽略，userId={}", userId);
            return;
        }

        StudyRoomAutoSignOutContext context = new StudyRoomAutoSignOutContext(userId);

        try {
            FilterResultDTO<?> filterResultDTO = filterChain.doFilter(context);
            if (!filterResultDTO.isSuccess()) {
                log.info("自动签退执行被过滤，userId={}，context={}，过滤原因={}", userId, GsonUtils.toJson(context), filterResultDTO.getErrorMsg());
                return;
            }

            StudyRoomVenueClientRespDTO venueClientRespDTO = context.getVenueClientRespDTO();
            StudyRoomIndexClientRespDTO indexClientRespDTO = context.getIndexClientRespDTO();

            StudyRoomSignOutClientReqDTO reqDTO = new StudyRoomSignOutClientReqDTO();
            reqDTO.setQrCode("签到专用码");
            reqDTO.setCurrentLng(venueClientRespDTO.getVenueLng());
            reqDTO.setCurrentLat(venueClientRespDTO.getVenueLat());
            reqDTO.setSettleUserPackageId(indexClientRespDTO.getSignInUserPackage().getSignInUserPackageId());
            reqDTO.setUserSignId(indexClientRespDTO.getUserSignId());
            signService.studySignOut(reqDTO, userId);
        } catch (Exception e) {
            log.error("自动签退执行失败，userId={}，context={}", userId, GsonUtils.toJson(context), e);
        } finally {
            RedisLockUtils.unlock(lockKey);
        }
    }
}
