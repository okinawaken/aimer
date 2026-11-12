package cn.nicomoe.aimer.study.service.autoreserve.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.constant.StudyRoomCacheConstants;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveTaskRecordStatusEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autoreserve.StudyRoomAutoReserveFilterChain;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoReserveContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveConfirmClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveExecutorService;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskRecordService;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskService;
import cn.nicomoe.aimer.study.service.client.StudyRoomReserveService;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.RedisLockUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 自动预约执行器实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudyRoomAutoReserveExecutorServiceImpl implements StudyRoomAutoReserveExecutorService {

    private final StudyRoomReserveService reserveService;
    private final StudyRoomAutoReserveTaskService taskService;
    private final StudyRoomAutoReserveTaskRecordService recordService;
    private final StudyRoomAutoReserveFilterChain filterChain;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(Long taskId, Integer triggerType) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = taskService.get(taskId);

        String lockKey = StudyRoomCacheConstants.AUTO_RESERVE_EXECUTE_LOCK + taskId;
        if (!RedisLockUtils.tryLock(lockKey)) {
            log.error("自动预约任务正在执行中，加锁失败，忽略，taskId={}，triggerType={}", taskId, triggerType);
            return;
        }

        StudyRoomAutoReserveContext context = new StudyRoomAutoReserveContext(taskInfoDTO, triggerType);

        try {
            FilterResultDTO<?> filterResultDTO = filterChain.doFilter(context);
            if (!filterResultDTO.isSuccess()) {
                log.info("自动预约任务被过滤，taskId={}，triggerType={}, context={}，过滤原因={}", taskId, triggerType, GsonUtils.toJson(context), filterResultDTO.getErrorMsg());
                StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO = this.buildTaskRecordDTO(context, StudyRoomAutoReserveTaskRecordStatusEnum.SKIPPED, filterResultDTO.getErrorMsg());
                recordService.saveRecord(taskRecordDTO);
                return;
            }

            LocalDate targetDate = context.getTargetDate();
            StudyRoomVenueClientRespDTO venueRespDTO = context.getVenueClientRespDTO();
            StudyRoomSeatClientRespDTO seatRespDTO = context.getSeatClientRespDTO();

            StudyRoomReserveConfirmClientReqDTO reqDTO = new StudyRoomReserveConfirmClientReqDTO();
            reqDTO.setSeatId(seatRespDTO.getId());
            reqDTO.setReserveDateStr(targetDate);
            reqDTO.setReserveStartTimeStr(taskInfoDTO.getReserveStartTime());
            reqDTO.setReserveEndTimeStr(taskInfoDTO.getReserveEndTime());
            reqDTO.setCurrentLng(venueRespDTO.getVenueLng());
            reqDTO.setCurrentLat(venueRespDTO.getVenueLat());
            reqDTO.setReserveAnotherFlag(0);
            StudyRoomReserveConfirmClientRespDTO reserveConfirmClientRespDTO = reserveService.reserveV2SeatConfirm(reqDTO, taskInfoDTO.getUserId());
            CheckUtils.throwIfNull(reserveConfirmClientRespDTO, "提交预约返回结果为空");
            context.setReserveConfirmClientRespDTO(reserveConfirmClientRespDTO);

            StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO = this.buildTaskRecordDTO(context, StudyRoomAutoReserveTaskRecordStatusEnum.SUCCESS, StrUtil.EMPTY);
            recordService.saveRecord(taskRecordDTO);
            taskService.advanceLastMaxReserveDate(taskId, context.getTargetDate());
        } catch (Exception e) {
            log.error("自动预约任务执行失败，taskId={}，triggerType={}, context={}", taskId, triggerType, GsonUtils.toJson(context), e);
            StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO = this.buildTaskRecordDTO(context, StudyRoomAutoReserveTaskRecordStatusEnum.FAILED, e.getMessage());
            recordService.saveRecord(taskRecordDTO);
        } finally {
            RedisLockUtils.unlock(lockKey);
        }
    }

    /**
     * 构建执行记录响应对象。
     *
     * @param context    执行上下文
     * @param statusEnum 终态
     * @param errorMsg   失败或跳过原因
     * @return 执行记录
     */
    private StudyRoomAutoReserveTaskRecordInfoDTO buildTaskRecordDTO(StudyRoomAutoReserveContext context, StudyRoomAutoReserveTaskRecordStatusEnum statusEnum, String errorMsg) {
        StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO = new StudyRoomAutoReserveTaskRecordInfoDTO();
        taskRecordDTO.setTriggerType(context.getTriggerType());
        taskRecordDTO.setTargetDate(context.getTargetDate());
        taskRecordDTO.setStatus(statusEnum.getValue());
        taskRecordDTO.setFailMsg(errorMsg);

        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = context.getTaskInfoDTO();
        if (Objects.nonNull(taskInfoDTO)) {
            taskRecordDTO.setTaskId(taskInfoDTO.getId());
            taskRecordDTO.setUserId(taskInfoDTO.getUserId());
            taskRecordDTO.setTimeRange(taskInfoDTO.getReserveStartTime() + "-" + taskInfoDTO.getReserveEndTime());
        }
        StudyRoomSeatClientRespDTO seatRespDTO = context.getSeatClientRespDTO();
        if (Objects.nonNull(seatRespDTO)) {
            taskRecordDTO.setSeatId(seatRespDTO.getId());
            taskRecordDTO.setSeatNumber(seatRespDTO.getSeatNumber());
        }
        StudyRoomReserveConfirmClientRespDTO confirmRespDTO = context.getReserveConfirmClientRespDTO();
        if (Objects.nonNull(confirmRespDTO)) {
            taskRecordDTO.setUserReserveId(confirmRespDTO.getId());
        }
        return taskRecordDTO;
    }
}
