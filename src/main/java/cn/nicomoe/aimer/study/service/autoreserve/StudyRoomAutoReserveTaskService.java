package cn.nicomoe.aimer.study.service.autoreserve;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveSeatOptionDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskReqDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskQuery;

import java.time.LocalDate;
import java.util.List;

/** 自动预约任务服务。 */
public interface StudyRoomAutoReserveTaskService {

    /** 查询指定用户的任务列表。 */
    List<StudyRoomAutoReserveTaskInfoDTO> list(Long userId, StudyRoomAutoReserveTaskQuery query);

    /** 查询指定用户的任务详情。 */
    StudyRoomAutoReserveTaskInfoDTO get(Long userId, Long id);

    /** 按主键查询任务详情，供执行器等无登录上下文的内部流程使用，不做归属校验。 */
    StudyRoomAutoReserveTaskInfoDTO getById(Long id);

    /** 创建任务。 */
    Long create(Long userId, StudyRoomAutoReserveTaskReqDTO req);

    /** 修改任务。 */
    void update(Long userId, StudyRoomAutoReserveTaskReqDTO req, Long id);

    /** 删除任务（级联删除执行记录）。 */
    void delete(Long userId, List<Long> ids);

    /** 以指定原因暂停任务。 */
    void pauseTask(Long userId, Long taskId, String pauseReason);

    /** 恢复任务。 */
    void resumeTask(Long userId, Long taskId);

    /** 手动执行一次。 */
    void executeTask(Long userId, Long taskId);

    /** 查询全部启用中的任务。 */
    List<StudyRoomAutoReserveTaskInfoDTO> listEnabledTasks();

    /** 条件推进放号基线日期。 */
    void advanceLastMaxReserveDate(Long taskId, LocalDate targetDate);

    /** 查询指定门店的区域 / 座位下拉选项。 */
    List<StudyRoomAutoReserveSeatOptionDTO> listSeatOptions(Integer venueId);
}
