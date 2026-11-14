package cn.nicomoe.aimer.aliyu.model.dto;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import lombok.Data;

/**
 * 阿里鱼用户配置数据传输对象。
 * <p>
 * 承载用户 ID 与配置内容，供跨层传输使用；Controller 会再转换为 VO 返回前端。
 *
 * @author bixiu
 * @since 4.3.0
 */
@Data
public class AliyuUserConfigDTO {

    /**
     * 系统用户 ID
     */
    private Long userId;

    /**
     * 阿里鱼用户配置（会话凭证束）
     */
    private AliyuUserConfigBO config;
}
