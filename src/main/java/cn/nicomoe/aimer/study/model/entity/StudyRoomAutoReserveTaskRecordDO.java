package cn.nicomoe.aimer.study.model.entity;

import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoReserveTaskRecordDetailBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import xyz.erupt.jpa.model.MetaModel;

import java.io.Serial;
import java.time.LocalDate;

/**
 * 自动预约执行记录实体（表 {@code study_room_auto_reserve_task_record}）。
 * <p>
 * 每轮抢座一条：仅在终态落库，跳过、成功、失败三种状态。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StudyRoomAutoReserveTaskRecordInfoDTO.class)
@Entity
@Table(name = "study_room_auto_reserve_task_record",
        indexes = @Index(name = "idx_task_id_create_time", columnList = "task_id,create_time"))
@Comment(value = "自动预约执行记录表")
@AttributeOverrides(value = {
        @AttributeOverride(name = "createTime", column = @Column(name = "create_time", nullable = false)),
        @AttributeOverride(name = "updateTime", column = @Column(name = "update_time", nullable = false))
})
public class StudyRoomAutoReserveTaskRecordDO extends MetaModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务 ID
     */
    @Column(name = "task_id", nullable = false)
    @Comment(value = "任务 ID")
    private Long taskId;

    /**
     * 系统用户 ID
     */
    @Column(name = "user_id", nullable = false)
    @Comment(value = "系统用户 ID")
    private Long userId;

    /**
     * 触发方式：0 定时，1 手动
     */
    @JdbcTypeCode(value = SqlTypes.TINYINT)
    @ColumnDefault(value = "0")
    @Column(name = "trigger_type", nullable = false)
    @Comment(value = "触发方式：0 定时，1 手动")
    private Integer triggerType;

    /**
     * 目标预约日期
     */
    @Column(name = "target_date", nullable = false)
    @Comment(value = "目标预约日期")
    private LocalDate targetDate;

    /**
     * 执行状态：0 跳过，1 成功，2 失败
     */
    @JdbcTypeCode(value = SqlTypes.TINYINT)
    @ColumnDefault(value = "0")
    @Column(name = "status", nullable = false)
    @Comment(value = "执行状态：0 跳过，1 成功，2 失败")
    private Integer status;

    /**
     * 尝试明细 JSON，结构见 {@link StudyRoomAutoReserveTaskRecordDetailBO}
     */
    @Column(name = "detail_json", nullable = false, length = 16_777_215)
    @Comment(value = "尝试明细 JSON")
    private String detailJson;
}
