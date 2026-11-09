package cn.nicomoe.aimer.study.model.dto;

import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import lombok.Data;

/**
 * 用户配置数据传输对象。
 * <p>
 * 承载用户 ID 与配置内容，供跨层传输使用；Controller 会再转换为 VO 返回前端。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomUserConfigDTO {

    /**
     * 系统用户 ID
     */
    private Long userId;

    /**
     * 自习室用户配置（放号时刻、自动签到开关、小程序 token）
     */
    private StudyRoomUserConfigBO config;
}
