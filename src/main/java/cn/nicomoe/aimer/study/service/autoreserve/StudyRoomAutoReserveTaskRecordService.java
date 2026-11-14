package cn.nicomoe.aimer.study.service.autoreserve;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskRecordQuery;

import java.time.LocalDate;
import java.util.List;

/** 自动预约执行记录服务。 */
public interface StudyRoomAutoReserveTaskRecordService {

    /** 查询指定用户的执行记录列表。 */
    List<StudyRoomAutoReserveTaskRecordInfoDTO> list(Long userId, StudyRoomAutoReserveTaskRecordQuery query);

    /** 查询指定用户的执行记录详情。 */
    StudyRoomAutoReserveTaskRecordInfoDTO get(Long userId, Long id);

    /** 批量删除指定用户任务的全部执行历史，供任务硬删时级联使用。 */
    void deleteByTaskIds(Long userId, List<Long> taskIds);

    /** 写入一条执行记录。 */
    void saveRecord(StudyRoomAutoReserveTaskRecordInfoDTO taskRecordDTO);

    /** 查询任务在指定目标日期的全部执行记录。 */
    List<StudyRoomAutoReserveTaskRecordInfoDTO> listByTaskIdAndTargetDate(Long taskId, LocalDate targetDate);
}
