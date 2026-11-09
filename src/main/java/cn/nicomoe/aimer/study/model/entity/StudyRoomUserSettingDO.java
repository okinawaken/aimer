package cn.nicomoe.aimer.study.model.entity;

import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;
import xyz.erupt.jpa.model.MetaModel;

import java.io.Serial;

/**
 * 自习室用户设置实体（表 {@code study_room_user_setting}）。
 * <p>
 * 一个系统用户一份配置，由唯一索引 {@code uk_user_id} 保证。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "study_room_user_setting",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_id", columnNames = "user_id"))
@Comment(value = "自习室用户设置表")
@AttributeOverrides(value = {
        @AttributeOverride(name = "createTime", column = @Column(name = "create_time", nullable = false)),
        @AttributeOverride(name = "updateTime", column = @Column(name = "update_time", nullable = false))
})
public class StudyRoomUserSettingDO extends MetaModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系统用户 ID
     */
    @Column(name = "user_id", nullable = false)
    @Comment(value = "系统用户 ID")
    private Long userId;

    /**
     * 配置 JSON，结构见 {@link StudyRoomUserConfigBO}
     */
    @Column(name = "config_json", nullable = false, length = 16_777_215)
    @Comment(value = "配置 JSON")
    private String configJson;
}
