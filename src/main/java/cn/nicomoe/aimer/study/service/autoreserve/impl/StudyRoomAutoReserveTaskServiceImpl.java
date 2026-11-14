package cn.nicomoe.aimer.study.service.autoreserve.impl;

import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveTaskEnableStatusEnum;
import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveTriggerTypeEnum;
import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoReserveTaskConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveSeatOptionDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatAreaClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatMapClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatQueryClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.model.entity.StudyRoomAutoReserveTaskDO;
import cn.nicomoe.aimer.study.model.event.StudyRoomAutoReserveExecuteEvent;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskQuery;
import cn.nicomoe.aimer.study.repository.StudyRoomAutoReserveTaskRepository;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskRecordService;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskService;
import cn.nicomoe.aimer.study.service.client.StudyRoomReserveService;
import cn.nicomoe.aimer.study.service.client.StudyRoomVenueService;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.erupt.jpa.dao.EruptDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudyRoomAutoReserveTaskServiceImpl implements StudyRoomAutoReserveTaskService {

    private final StudyRoomAutoReserveTaskRepository taskRepository;
    private final StudyRoomAutoReserveTaskRecordService recordService;
    private final StudyRoomVenueService venueService;
    private final StudyRoomReserveService reserveService;
    private final ApplicationEventPublisher eventPublisher;
    private final EruptDao eruptDao;

    @Override
    public List<StudyRoomAutoReserveTaskInfoDTO> list(Long userId, StudyRoomAutoReserveTaskQuery query) {
        List<StudyRoomAutoReserveTaskDO> taskList = eruptDao.lambdaQuery(StudyRoomAutoReserveTaskDO.class)
                .eq(StudyRoomAutoReserveTaskDO::getUserId, userId)
                .eq(Objects.nonNull(query.getVenueId()), StudyRoomAutoReserveTaskDO::getVenueId, query.getVenueId())
                .eq(Objects.nonNull(query.getEnableStatus()), StudyRoomAutoReserveTaskDO::getEnableStatus, query.getEnableStatus())
                .orderByDesc(StudyRoomAutoReserveTaskDO::getId)
                .list();
        return taskList.stream().map(this::toInfoDTO).toList();
    }

    @Override
    public StudyRoomAutoReserveTaskInfoDTO get(Long userId, Long id) {
        return this.toInfoDTO(this.getTaskDo(id, userId));
    }

    @Override
    public StudyRoomAutoReserveTaskInfoDTO getById(Long id) {
        return this.toInfoDTO(this.findTaskDo(id));
    }

    @Override
    public Long create(Long userId, StudyRoomAutoReserveTaskReqDTO req) {
        StudyRoomVenueClientRespDTO venueClientRespDTO = venueService.reserveVenueDetail(req.getVenueId());
        CheckUtils.throwIfNull(venueClientRespDTO, "未查询到门店数据");
        StudyRoomAutoReserveTaskConfigBO configBO = MapstructUtils.convert(req, StudyRoomAutoReserveTaskConfigBO.class);

        StudyRoomAutoReserveTaskDO taskDO = new StudyRoomAutoReserveTaskDO();
        taskDO.setUserId(userId);
        taskDO.setVenueId(req.getVenueId());
        taskDO.setVenueName(venueClientRespDTO.getVenueName());
        taskDO.setTaskJson(GsonUtils.toJson(configBO));
        taskDO.setEnableStatus(StudyRoomAutoReserveTaskEnableStatusEnum.ENABLED.getValue());
        taskRepository.save(taskDO);
        return taskDO.getId();
    }

    @Override
    public void update(Long userId, StudyRoomAutoReserveTaskReqDTO req, Long id) {
        StudyRoomAutoReserveTaskDO taskDO = this.getTaskDo(id, userId);
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
    public void delete(Long userId, List<Long> ids) {
        CheckUtils.throwIfEmpty(ids, "任务 ID 不能为空");
        eruptDao.lambdaQuery(StudyRoomAutoReserveTaskDO.class)
                .eq(StudyRoomAutoReserveTaskDO::getUserId, userId)
                .in(StudyRoomAutoReserveTaskDO::getId, ids)
                .delete();
        recordService.deleteByTaskIds(userId, ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pauseTask(Long userId, Long taskId, String pauseReason) {
        StudyRoomAutoReserveTaskDO task = this.getTaskDo(taskId, userId);
        task.setEnableStatus(StudyRoomAutoReserveTaskEnableStatusEnum.PAUSED.getValue());
        task.setPauseReason(pauseReason);
        taskRepository.save(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resumeTask(Long userId, Long taskId) {
        StudyRoomAutoReserveTaskDO task = this.getTaskDo(taskId, userId);
        task.setEnableStatus(StudyRoomAutoReserveTaskEnableStatusEnum.ENABLED.getValue());
        task.setPauseReason(StrUtil.EMPTY);
        taskRepository.save(task);
    }

    @Override
    public void executeTask(Long userId, Long taskId) {
        StudyRoomAutoReserveTaskDO task = this.getTaskDo(taskId, userId);
        CheckUtils.throwIf(!StudyRoomAutoReserveTaskEnableStatusEnum.isEnabled(task.getEnableStatus()), "任务已暂停，请先恢复");
        eventPublisher.publishEvent(new StudyRoomAutoReserveExecuteEvent(task.getId(), StudyRoomAutoReserveTriggerTypeEnum.MANUAL.getValue()));
    }

    @Override
    public List<StudyRoomAutoReserveTaskInfoDTO> listEnabledTasks() {
        return taskRepository.findByEnableStatus(StudyRoomAutoReserveTaskEnableStatusEnum.ENABLED.getValue())
                .stream()
                .map(this::toInfoDTO)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void advanceLastMaxReserveDate(Long taskId, LocalDate targetDate) {
        taskRepository.advanceLastMaxReserveDate(taskId, targetDate);
    }

    @Override
    public List<StudyRoomAutoReserveSeatOptionDTO> listSeatOptions(Integer venueId) {
        StudyRoomSeatQueryClientReqDTO seatQuery = new StudyRoomSeatQueryClientReqDTO();
        seatQuery.setVenueId(venueId);
        seatQuery.setReserveDateStr(LocalDate.now());
        seatQuery.setReserveAnotherFlag(0);
        StudyRoomSeatMapClientRespDTO seatMapDTO = reserveService.reserveV2SeatList(seatQuery);
        CheckUtils.throwIfNull(seatMapDTO, "未查询到门店座位信息");
        List<StudyRoomAutoReserveSeatOptionDTO> seatOptionList = new ArrayList<>();
        for (StudyRoomSeatAreaClientRespDTO seatAreaDTO : seatMapDTO.getVenueAreaList()) {
            for (StudyRoomSeatClientRespDTO seatDTO : seatAreaDTO.getVenueAreaSeatList()) {
                StudyRoomAutoReserveSeatOptionDTO seatOptionDTO = new StudyRoomAutoReserveSeatOptionDTO();
                seatOptionDTO.setAreaId(seatAreaDTO.getId());
                seatOptionDTO.setAreaName(seatAreaDTO.getAreaName());
                seatOptionDTO.setSeatId(seatDTO.getId());
                seatOptionDTO.setSeatNumber(seatDTO.getSeatNumber());
                seatOptionDTO.setSeatReserveState(seatDTO.getSeatReserveState());
                seatOptionDTO.setSeatReserveDesc(seatDTO.getSeatReserveDesc());
                seatOptionList.add(seatOptionDTO);
            }
        }
        return seatOptionList;
    }

    private StudyRoomAutoReserveTaskDO getTaskDo(Long id, Long userId) {
        StudyRoomAutoReserveTaskDO taskDO = eruptDao.lambdaQuery(StudyRoomAutoReserveTaskDO.class)
                .eq(StudyRoomAutoReserveTaskDO::getId, id)
                .eq(StudyRoomAutoReserveTaskDO::getUserId, userId)
                .one();
        CheckUtils.throwIfNull(taskDO, "任务不存在");
        return taskDO;
    }

    private StudyRoomAutoReserveTaskDO findTaskDo(Long id) {
        StudyRoomAutoReserveTaskDO taskDO = taskRepository.findById(id).orElse(null);
        CheckUtils.throwIfNull(taskDO, "任务不存在");
        return taskDO;
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
