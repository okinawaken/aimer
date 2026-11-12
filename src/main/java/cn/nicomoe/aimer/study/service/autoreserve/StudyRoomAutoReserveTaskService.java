package cn.nicomoe.aimer.study.service.autoreserve;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveSeatOptionDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskReqDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskQuery;

import java.time.LocalDate;
import java.util.List;

/**
 * 自动预约任务服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomAutoReserveTaskService {

    /**
     * 查询任务列表（仅当前用户）。
     *
     * @param query 查询条件
     * @return 列表数据
     */
    List<StudyRoomAutoReserveTaskInfoDTO> list(StudyRoomAutoReserveTaskQuery query);

    /**
     * 查询任务详情。
     *
     * @param id 任务 ID
     * @return 详情数据
     */
    StudyRoomAutoReserveTaskInfoDTO get(Long id);

    /**
     * 创建任务。
     *
     * @param req 请求参数
     * @return 新任务 ID
     */
    Long create(StudyRoomAutoReserveTaskReqDTO req);

    /**
     * 修改任务。
     *
     * @param req 请求参数
     * @param id  任务 ID
     */
    void update(StudyRoomAutoReserveTaskReqDTO req, Long id);

    /**
     * 删除任务（级联删除执行记录）。
     *
     * @param ids 任务 ID 集合
     */
    void delete(List<Long> ids);

    /**
     * 以指定原因暂停任务。
     *
     * @param taskId      任务 ID
     * @param pauseReason 暂停原因
     */
    void pauseTask(Long taskId, String pauseReason);

    /**
     * 恢复任务。
     *
     * @param taskId 任务 ID
     */
    void resumeTask(Long taskId);

    /**
     * 手动执行一次。
     *
     * @param taskId 任务 ID
     */
    void executeTask(Long taskId);

    /**
     * 查询全部启用中的任务。
     *
     * @return 列表数据
     */
    List<StudyRoomAutoReserveTaskInfoDTO> listEnabledTasks();

    /**
     * 条件推进放号基线日期。
     *
     * @param taskId     任务 ID
     * @param targetDate 目标预约日期
     */
    void advanceLastMaxReserveDate(Long taskId, LocalDate targetDate);

    /**
     * 查询指定门店的区域 / 座位下拉选项。
     *
     * @param venueId 门店 ID
     * @return 座位选项列表（一行一个座位，含区域信息）
     */
    List<StudyRoomAutoReserveSeatOptionDTO> listSeatOptions(Integer venueId);
}
