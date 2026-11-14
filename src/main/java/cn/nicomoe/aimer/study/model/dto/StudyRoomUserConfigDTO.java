package cn.nicomoe.aimer.study.model.dto;

import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import lombok.Data;

/** 用户配置的跨层载体（用户 ID + 配置内容）。 */
@Data
public class StudyRoomUserConfigDTO {

    /** 系统用户 ID */
    private Long userId;

    /** 自习室用户配置（放号时刻、自动签到开关、小程序 token） */
    private StudyRoomUserConfigBO config;
}
