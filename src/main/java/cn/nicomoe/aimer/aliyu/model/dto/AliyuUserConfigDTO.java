package cn.nicomoe.aimer.aliyu.model.dto;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserSettingConfigBO;
import lombok.Data;

/**
 * 用户配置的跨层载体（用户 ID + 配置内容）。
 */
@Data
public class AliyuUserConfigDTO {

    /**
     * 系统用户 ID
     */
    private Long userId;

    /**
     * 阿里鱼用户配置（短时会话标识）
     */
    private AliyuUserSettingConfigBO config;
}
