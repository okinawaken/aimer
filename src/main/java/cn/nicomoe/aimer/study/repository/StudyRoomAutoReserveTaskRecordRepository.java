package cn.nicomoe.aimer.study.repository;

import cn.nicomoe.aimer.study.model.entity.StudyRoomAutoReserveTaskRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StudyRoomAutoReserveTaskRecordRepository extends JpaRepository<StudyRoomAutoReserveTaskRecordDO, Long> {

    /**
     * 查询任务在指定目标日期的执行记录。 返回：记录列表
     */
    List<StudyRoomAutoReserveTaskRecordDO> findByTaskIdAndTargetDate(Long taskId, LocalDate targetDate);
}
