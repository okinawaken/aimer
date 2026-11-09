package cn.nicomoe.aimer.study.service.autoreserve.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveTaskEnableStatusEnum;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveTriggerTypeEnum;
import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoReserveTaskConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.model.entity.StudyRoomAutoReserveTaskDO;
import cn.nicomoe.aimer.study.model.event.StudyRoomAutoReserveExecuteEvent;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskQuery;
import cn.nicomoe.aimer.study.repository.StudyRoomAutoReserveTaskRepository;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskRecordService;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskService;
import cn.nicomoe.aimer.study.service.client.StudyRoomVenueService;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.service.EruptUserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 自动预约任务服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomAutoReserveTaskServiceImpl implements StudyRoomAutoReserveTaskService {

    private final StudyRoomAutoReserveTaskRepository taskRepository;
    private final StudyRoomAutoReserveTaskRecordService recordService;
    private final StudyRoomVenueService venueService;
    private final ApplicationEventPublisher eventPublisher;
    private final EruptUserService eruptUserService;
    private final EruptDao eruptDao;

    @Override
    public List<StudyRoomAutoReserveTaskInfoDTO> list(StudyRoomAutoReserveTaskQuery query) {
        Long userId = eruptUserService.getCurrentUid();
        List<StudyRoomAutoReserveTaskDO> taskList = eruptDao.lambdaQuery(StudyRoomAutoReserveTaskDO.class)
                .eq(StudyRoomAutoReserveTaskDO::getUserId, userId)
                .eq(Objects.nonNull(query.getVenueId()), StudyRoomAutoReserveTaskDO::getVenueId, query.getVenueId())
                .eq(Objects.nonNull(query.getEnableStatus()), StudyRoomAutoReserveTaskDO::getEnableStatus, query.getEnableStatus())
                .orderByDesc(StudyRoomAutoReserveTaskDO::getId)
                .list();
        return taskList.stream().map(this::toInfoDTO).toList();
    }

    @Override
    public StudyRoomAutoReserveTaskInfoDTO get(Long id) {
        return toInfoDTO(this.getTaskDo(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(StudyRoomAutoReserveTaskReqDTO req) {
        StudyRoomVenueClientRespDTO venueClientRespDTO = venueService.reserveVenueDetail(req.getVenueId());
        CheckUtils.throwIfNull(venueClientRespDTO, "未查询到门店数据");
        StudyRoomAutoReserveTaskConfigBO configBO = MapstructUtils.convert(req, StudyRoomAutoReserveTaskConfigBO.class);
        Long currentUid = eruptUserService.getCurrentUid();

        StudyRoomAutoReserveTaskDO taskDO = new StudyRoomAutoReserveTaskDO();
        taskDO.setUserId(currentUid);
        taskDO.setVenueId(req.getVenueId());
        taskDO.setVenueName(venueClientRespDTO.getVenueName());
        taskDO.setTaskJson(GsonUtils.toJson(configBO));
        taskDO.setEnableStatus(StudyRoomAutoReserveTaskEnableStatusEnum.ENABLED.getValue());
        taskRepository.save(taskDO);
        return taskDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudyRoomAutoReserveTaskReqDTO req, Long id) {
        StudyRoomAutoReserveTaskDO taskDO = this.getTaskDo(id);
        StudyRoomVenueClientRespDTO venueClientRespDTO = venueService.reserveVenueDetail(req.getVenueId());
        CheckUtils.throwIfNull(venueClientRespDTO, "未查询到门店数据");
        StudyRoomAutoReserveTaskConfigBO configBO = MapstructUtils.convert(req, StudyRoomAutoReserveTaskConfigBO.class);

        taskDO.setVenueId(req.getVenueId());
        taskDO.setVenueName(venueClientRespDTO.getVenueName());
        taskDO.setTaskJson(GsonUtils.toJson(configBO));
        taskRepository.save(taskDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            taskRepository.deleteById(id);
            recordService.deleteByTaskId(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pauseTask(Long taskId, String pauseReason) {
        StudyRoomAutoReserveTaskDO task = this.getTaskDo(taskId);
        task.setEnableStatus(StudyRoomAutoReserveTaskEnableStatusEnum.PAUSED.getValue());
        task.setPauseReason(pauseReason);
        taskRepository.save(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resumeTask(Long taskId) {
        StudyRoomAutoReserveTaskDO task = this.getTaskDo(taskId);
        task.setEnableStatus(StudyRoomAutoReserveTaskEnableStatusEnum.ENABLED.getValue());
        task.setPauseReason(StrUtil.EMPTY);
        taskRepository.save(task);
    }

    @Override
    public void executeTask(Long taskId) {
        StudyRoomAutoReserveTaskDO task = this.getTaskDo(taskId);
        CheckUtils.throwIf(!StudyRoomAutoReserveTaskEnableStatusEnum.isEnabled(task.getEnableStatus()), "任务已暂停，请先恢复");
        eventPublisher.publishEvent(new StudyRoomAutoReserveExecuteEvent(task.getId(), StudyRoomAutoReserveTriggerTypeEnum.MANUAL.getValue()));
    }

    @Override
    public List<StudyRoomAutoReserveTaskInfoDTO> listEnabledTasks() {
        return taskRepository
                .findByEnableStatus(StudyRoomAutoReserveTaskEnableStatusEnum.ENABLED.getValue())
                .stream()
                .map(this::toInfoDTO)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void advanceLastMaxReserveDate(Long taskId, LocalDate targetDate) {
        taskRepository.advanceLastMaxReserveDate(taskId, targetDate);
    }

    private StudyRoomAutoReserveTaskDO getTaskDo(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EruptWebApiRuntimeException("任务不存在"));
    }

    private StudyRoomAutoReserveTaskInfoDTO toInfoDTO(StudyRoomAutoReserveTaskDO taskDO) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = MapstructUtils.convert(taskDO, StudyRoomAutoReserveTaskInfoDTO.class);
        StudyRoomAutoReserveTaskConfigBO configBO = GsonUtils.fromJson(taskDO.getTaskJson(), StudyRoomAutoReserveTaskConfigBO.class);
        if (Objects.nonNull(configBO)) {
            taskInfoDTO.setSeatId(configBO.getSeatId());
            taskInfoDTO.setSeatNumber(configBO.getSeatNumber());
            taskInfoDTO.setAreaId(configBO.getAreaId());
            taskInfoDTO.setAreaName(configBO.getAreaName());
            taskInfoDTO.setReserveStartTime(configBO.getReserveStartTime());
            taskInfoDTO.setReserveEndTime(configBO.getReserveEndTime());
            taskInfoDTO.setWeekDays(configBO.getWeekDays());
        }
        return taskInfoDTO;
    }
}
