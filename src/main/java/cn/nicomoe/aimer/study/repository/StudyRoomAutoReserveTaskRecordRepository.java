package cn.nicomoe.aimer.study.repository;

import cn.nicomoe.aimer.study.model.entity.StudyRoomAutoReserveTaskRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
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
     * 查询任务在指定时间区间内的执行记录。
     *
     * @param taskId    任务 ID
     * @param startTime 起始时间（含）
     * @param endTime   结束时间（不含）
     * @return 记录列表
     */
    List<StudyRoomAutoReserveTaskRecordDO> findByTaskIdAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(Long taskId, LocalDateTime startTime, LocalDateTime endTime);
}
