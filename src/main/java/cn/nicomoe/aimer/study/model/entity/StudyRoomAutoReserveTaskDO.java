package cn.nicomoe.aimer.study.model.entity;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveTaskEnableStatusEnum;
import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoReserveTaskConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
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
 * 自动预约任务实体（表 {@code study_room_auto_reserve_task}）。
 * <p>
 * 规则详情（座位、区域、时段、星期）存 {@code task_json}；查询与索引相关字段独立成列。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StudyRoomAutoReserveTaskInfoDTO.class)
@Entity
@Table(name = "study_room_auto_reserve_task",
        indexes = {
                @Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_enable_status", columnList = "enable_status")
        })
@Comment(value = "自动预约任务表")
@AttributeOverrides(value = {
        @AttributeOverride(name = "createTime", column = @Column(name = "create_time", nullable = false)),
        @AttributeOverride(name = "updateTime", column = @Column(name = "update_time", nullable = false))
})
public class StudyRoomAutoReserveTaskDO extends MetaModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系统用户 ID
     */
    @Column(name = "user_id", nullable = false)
    @Comment(value = "系统用户 ID")
    private Long userId;

    /**
     * 门店 ID
     */
    @Column(name = "venue_id", nullable = false)
    @Comment(value = "门店 ID")
    private Integer venueId;

    /**
     * 门店名称（冗余，避免列表页反复调上游）
     */
    @Column(name = "venue_name", nullable = false, length = 128)
    @Comment(value = "门店名称")
    private String venueName;

    /**
     * 任务详情 JSON，结构见 {@link StudyRoomAutoReserveTaskConfigBO}
     */
    @Column(name = "task_json", nullable = false, length = 16_777_215)
    @Comment(value = "任务详情 JSON")
    private String taskJson;

    /**
     * 启用状态，见 {@link StudyRoomAutoReserveTaskEnableStatusEnum}
     */
    @JdbcTypeCode(value = SqlTypes.TINYINT)
    @ColumnDefault(value = "1")
    @Column(name = "enable_status", nullable = false)
    @Comment(value = "是否启用：0 暂停，1 启用")
    private Integer enableStatus;

    /**
     * 暂停原因
     */
    @Column(name = "pause_reason")
    @Comment(value = "暂停原因")
    private String pauseReason;

    /**
     * 放号基线日期
     */
    @Column(name = "last_max_reserve_date")
    @Comment(value = "放号基线日期")
    private LocalDate lastMaxReserveDate;
}
