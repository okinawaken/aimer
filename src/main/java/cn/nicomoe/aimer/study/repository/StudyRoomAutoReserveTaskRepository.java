package cn.nicomoe.aimer.study.repository;

import cn.nicomoe.aimer.study.model.entity.StudyRoomAutoReserveTaskDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 自动预约任务数据访问层。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomAutoReserveTaskRepository extends JpaRepository<StudyRoomAutoReserveTaskDO, Long> {

    /**
     * 按启用状态查询任务列表。
     *
     * @param enableStatus 启用状态
     * @return 任务列表
     */
    List<StudyRoomAutoReserveTaskDO> findByEnableStatus(Integer enableStatus);

    /**
     * 条件推进放号基线日期，仅当目标日期大于当前基线时才更新。
     *
     * @param taskId     任务 ID
     * @param targetDate 目标预约日期
     */
    @Modifying
    @Query(value = "update StudyRoomAutoReserveTaskDO t set t.lastMaxReserveDate = :targetDate where t.id = :taskId and (t.lastMaxReserveDate is null or t.lastMaxReserveDate < :targetDate)")
    void advanceLastMaxReserveDate(@Param(value = "taskId") Long taskId, @Param(value = "targetDate") LocalDate targetDate);
}
