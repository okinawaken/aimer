package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 用户配置响应视图对象
 * <p>
 * 承载用户 ID 与配置内容，返回给前端展示。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomUserConfigDTO.class)
public class StudyRoomUserConfigVO {

    /**
     * 系统用户 ID
     */
    private Long userId;

    /**
     * 自习室用户配置（放号时刻、自动签到开关、小程序 token）
     */
    private StudyRoomUserConfigBO config;
}
