package cn.nicomoe.aimer.study.service.autoreserve.impl;

import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoReserveTaskRecordDetailBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import cn.nicomoe.aimer.study.model.entity.StudyRoomAutoReserveTaskRecordDO;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskRecordQuery;
import cn.nicomoe.aimer.study.repository.StudyRoomAutoReserveTaskRecordRepository;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskRecordService;
import cn.nicomoe.aimer.util.GsonUtils;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.jpa.dao.EruptDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 自动预约执行记录服务实现。
 * <p>
 * 任务归属独立成 {@code task_id} 列，按任务查询与删除直接走数据库；执行细节仍在 {@code detail_json} 中。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomAutoReserveTaskRecordServiceImpl implements StudyRoomAutoReserveTaskRecordService {

    private final StudyRoomAutoReserveTaskRecordRepository recordRepository;
    private final EruptDao eruptDao;

    @Override
    public List<StudyRoomAutoReserveTaskRecordInfoDTO> list(StudyRoomAutoReserveTaskRecordQuery query) {
        List<StudyRoomAutoReserveTaskRecordDO> recordList = eruptDao.lambdaQuery(StudyRoomAutoReserveTaskRecordDO.class)
                .eq(Objects.nonNull(query.getTaskId()), StudyRoomAutoReserveTaskRecordDO::getTaskId, query.getTaskId())
                .eq(Objects.nonNull(query.getUserId()), StudyRoomAutoReserveTaskRecordDO::getUserId, query.getUserId())
                .eq(Objects.nonNull(query.getTriggerType()), StudyRoomAutoReserveTaskRecordDO::getTriggerType, query.getTriggerType())
                .eq(Objects.nonNull(query.getTargetDate()), StudyRoomAutoReserveTaskRecordDO::getTargetDate, query.getTargetDate())
                .eq(Objects.nonNull(query.getStatus()), StudyRoomAutoReserveTaskRecordDO::getStatus, query.getStatus())
                .orderByDesc(StudyRoomAutoReserveTaskRecordDO::getCreateTime)
                .list();
        return recordList.stream().map(this::toInfoDTO).toList();
    }

    @Override
    public StudyRoomAutoReserveTaskRecordInfoDTO get(Long id) {
        StudyRoomAutoReserveTaskRecordDO recordDO = recordRepository.findById(id).orElseThrow(() -> new EruptWebApiRuntimeException("任务记录不存在"));
        return toInfoDTO(recordDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTaskId(Long taskId) {
        recordRepository.deleteByTaskId(taskId);
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
    @Transactional(rollbackFor = Exception.class)
    public void updateRecord(StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO) {
        StudyRoomAutoReserveTaskRecordDO recordDO = recordRepository.findById(taskRecordDTO.getId()).orElseThrow(() -> new EruptWebApiRuntimeException("未查询到任务记录数据"));

        StudyRoomAutoReserveTaskRecordDetailBO detailBO = MapstructUtils.convert(taskRecordDTO, StudyRoomAutoReserveTaskRecordDetailBO.class);
        recordDO.setStatus(taskRecordDTO.getStatus());
        recordDO.setDetailJson(GsonUtils.toJson(detailBO));
        recordRepository.save(recordDO);
    }

    @Override
    public List<StudyRoomAutoReserveTaskRecordInfoDTO> listByTaskIdAndCreateDate(Long taskId, LocalDate createDate) {
        List<StudyRoomAutoReserveTaskRecordDO> recordList = recordRepository.findByTaskIdAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(taskId, createDate.atStartOfDay(), createDate.plusDays(1).atStartOfDay());
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
