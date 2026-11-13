package cn.nicomoe.aimer.study.service.autosignout.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.constant.StudyRoomCacheConstants;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosignout.StudyRoomAutoSignOutFilterChain;
import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoSignOutSessionBO;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignOutClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSignOutClientRespDTO;
import cn.nicomoe.aimer.study.service.autosignout.StudyRoomAutoSignOutExecutorService;
import cn.nicomoe.aimer.study.service.client.StudyRoomSignService;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.RedisLockUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
                log.info("自动签退执行被过滤，userId={}，过滤原因={}", userId, filterResultDTO.getErrorMsg());
                return;
            }
            this.signOutAll(context);
        } catch (Exception e) {
            log.error("自动签退执行失败，userId={}，context={}", userId, GsonUtils.toJson(context), e);
        } finally {
            RedisLockUtils.unlock(lockKey);
        }
    }

    /**
     * 逐条签退全部在座记录，单条异常不影响后续记录。
     *
     * @param context 执行上下文
     */
    private void signOutAll(StudyRoomAutoSignOutContext context) {
        List<StudyRoomAutoSignOutSessionBO> sessionBOList = context.getSessionBOList();
        for (StudyRoomAutoSignOutSessionBO sessionBO : sessionBOList) {
            try {
                StudyRoomSignOutClientRespDTO signOutRespDTO = this.signOut(context.getUserId(), sessionBO);
                if (Objects.isNull(signOutRespDTO)) {
                    log.warn("自动签退返回为空，userSignId={}", sessionBO.getUserSignId());
                }
            } catch (Exception e) {
                log.error("自动签退失败，userSignId={}，venueName={}", sessionBO.getUserSignId(), sessionBO.getVenueName(), e);
            }
        }
    }

    /**
     * 发起单条签退请求。
     *
     * @param userId    用户 ID
     * @param sessionBO 签退执行计划
     * @return 签退结果
     */
    private StudyRoomSignOutClientRespDTO signOut(Long userId, StudyRoomAutoSignOutSessionBO sessionBO) {
        StudyRoomSignOutClientReqDTO reqDTO = new StudyRoomSignOutClientReqDTO();
        reqDTO.setQrCode(StrUtil.EMPTY);
        reqDTO.setCurrentLng(sessionBO.getCurrentLng());
        reqDTO.setCurrentLat(sessionBO.getCurrentLat());
        reqDTO.setSettleUserPackageId(sessionBO.getSettleUserPackageId());
        reqDTO.setUserSignId(sessionBO.getUserSignId());
        return signService.studySignOut(reqDTO, userId);
    }
}
