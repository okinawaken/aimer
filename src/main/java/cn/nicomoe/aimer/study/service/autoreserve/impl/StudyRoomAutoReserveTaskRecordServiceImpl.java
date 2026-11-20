package cn.nicomoe.aimer.study.service.autoreserve.impl;

import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoReserveTaskRecordDetailBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import cn.nicomoe.aimer.study.model.entity.StudyRoomAutoReserveTaskRecordDO;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskRecordQuery;
import cn.nicomoe.aimer.study.repository.StudyRoomAutoReserveTaskRecordRepository;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskRecordService;
import cn.nicomoe.aimer.util.CheckUtils;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.erupt.jpa.dao.EruptDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 执行记录服务：任务归属单独落在 {@code task_id} 列，执行明细在 {@code detail_json}。
 */
@Service
@RequiredArgsConstructor
public class StudyRoomAutoReserveTaskRecordServiceImpl implements StudyRoomAutoReserveTaskRecordService {

    private final EruptDao eruptDao;
    private final StudyRoomAutoReserveTaskRecordRepository recordRepository;

    @Override
    public List<StudyRoomAutoReserveTaskRecordInfoDTO> list(Long userId, StudyRoomAutoReserveTaskRecordQuery query) {
        List<StudyRoomAutoReserveTaskRecordDO> recordList = eruptDao.lambdaQuery(StudyRoomAutoReserveTaskRecordDO.class)
                .eq(StudyRoomAutoReserveTaskRecordDO::getUserId, userId)
                .eq(Objects.nonNull(query.getTaskId()), StudyRoomAutoReserveTaskRecordDO::getTaskId, query.getTaskId())
                .eq(Objects.nonNull(query.getTriggerType()), StudyRoomAutoReserveTaskRecordDO::getTriggerType, query.getTriggerType())
                .eq(Objects.nonNull(query.getTargetDate()), StudyRoomAutoReserveTaskRecordDO::getTargetDate, query.getTargetDate())
                .eq(Objects.nonNull(query.getStatus()), StudyRoomAutoReserveTaskRecordDO::getStatus, query.getStatus())
                .orderByDesc(StudyRoomAutoReserveTaskRecordDO::getCreateTime)
                .list();
        return recordList.stream().map(this::toInfoDTO).toList();
    }

    @Override
    public StudyRoomAutoReserveTaskRecordInfoDTO get(Long userId, Long id) {
        StudyRoomAutoReserveTaskRecordDO recordDO = eruptDao.lambdaQuery(StudyRoomAutoReserveTaskRecordDO.class)
                .eq(StudyRoomAutoReserveTaskRecordDO::getId, id)
                .eq(StudyRoomAutoReserveTaskRecordDO::getUserId, userId)
                .one();
        CheckUtils.throwIfNull(recordDO, "任务记录不存在");
        return this.toInfoDTO(recordDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTaskIds(Long userId, List<Long> taskIds) {
        eruptDao.lambdaQuery(StudyRoomAutoReserveTaskRecordDO.class)
                .eq(StudyRoomAutoReserveTaskRecordDO::getUserId, userId)
                .in(StudyRoomAutoReserveTaskRecordDO::getTaskId, taskIds)
                .delete();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRecord(StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO) {
        StudyRoomAutoReserveTaskRecordDetailBO detailBO = MapstructUtils.convert(taskRecordDTO, StudyRoomAutoReserveTaskRecordDetailBO.class);

        StudyRoomAutoReserveTaskRecordDO recordDO = new StudyRoomAutoReserveTaskRecordDO();
        recordDO.setTaskId(taskRecordDTO.getTaskId());
        recordDO.setUserId(taskRecordDTO.getUserId());
        recordDO.setTriggerType(taskRecordDTO.getTriggerType());
        recordDO.setTargetDate(taskRecordDTO.getTargetDate());
        recordDO.setStatus(taskRecordDTO.getStatus());
        recordDO.setDetailJson(GsonUtils.toJson(detailBO));
        recordRepository.save(recordDO);
    }

    @Override
    public List<StudyRoomAutoReserveTaskRecordInfoDTO> listByTaskIdAndTargetDate(Long taskId, LocalDate targetDate) {
        List<StudyRoomAutoReserveTaskRecordDO> recordList = recordRepository.findByTaskIdAndTargetDate(taskId, targetDate);
        return recordList.stream().map(this::toInfoDTO).toList();
    }

    private StudyRoomAutoReserveTaskRecordInfoDTO toInfoDTO(StudyRoomAutoReserveTaskRecordDO recordDO) {
        StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO = MapstructUtils.convert(recordDO, StudyRoomAutoReserveTaskRecordInfoDTO.class);
        StudyRoomAutoReserveTaskRecordDetailBO detailBO = GsonUtils.fromJson(recordDO.getDetailJson(), StudyRoomAutoReserveTaskRecordDetailBO.class);
        if (Objects.nonNull(detailBO)) {
            taskRecordDTO.setSeatId(detailBO.getSeatId());
            taskRecordDTO.setSeatNumber(detailBO.getSeatNumber());
            taskRecordDTO.setTimeRange(detailBO.getTimeRange());
            taskRecordDTO.setUserReserveId(detailBO.getUserReserveId());
            taskRecordDTO.setFailMsg(detailBO.getFailMsg());
        }
        return taskRecordDTO;
    }
}
