package cn.nicomoe.aimer.study.repository;

import cn.nicomoe.aimer.study.model.entity.StudyRoomAutoReserveTaskRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * 自动预约执行记录数据访问层。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomAutoReserveTaskRecordRepository extends JpaRepository<StudyRoomAutoReserveTaskRecordDO, Long> {

    /**
     * 删除任务的全部执行记录。
     *
     * @param taskId 任务 ID
     */
    void deleteByTaskId(Long taskId);

    /**
     * 查询任务在指定目标日期的执行记录。
     *
     * @param taskId     任务 ID
     * @param targetDate 目标预约日期
     * @return 记录列表
     */
    List<StudyRoomAutoReserveTaskRecordDO> findByTaskIdAndTargetDate(Long taskId, LocalDate targetDate);
}
