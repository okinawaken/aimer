package cn.nicomoe.aimer.study.service.autoreserve;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskRecordQuery;

import java.time.LocalDate;
import java.util.List;

/**
 * 自动预约执行记录服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomAutoReserveTaskRecordService {

    /**
     * 查询执行记录列表。
     *
     * @param query 查询条件
     * @return 列表数据
     */
    List<StudyRoomAutoReserveTaskRecordInfoDTO> list(StudyRoomAutoReserveTaskRecordQuery query);

    /**
     * 查询执行记录详情。
     *
     * @param id 记录 ID
     * @return 详情数据
     */
    StudyRoomAutoReserveTaskRecordInfoDTO get(Long id);

    /**
     * 删除任务的全部执行历史，供任务硬删时级联使用。
     *
     * @param taskId 任务 ID
     */
    void deleteByTaskId(Long taskId);

    /**
     * 写入一条执行记录。
     *
     * @param taskRecordDTO 任务记录
     */
    void saveRecord(StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO);

    /**
     * 按主键更新一条执行记录。
     *
     * @param taskRecordDTO 任务记录
     */
    void updateRecord(StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO);

    /**
     * 查询任务在指定日期的全部执行记录。
     *
     * @param taskId     任务 ID
     * @param createDate 创建日期
     * @return 列表数据
     */
    List<StudyRoomAutoReserveTaskRecordInfoDTO> listByTaskIdAndCreateDate(Long taskId, LocalDate createDate);
}
