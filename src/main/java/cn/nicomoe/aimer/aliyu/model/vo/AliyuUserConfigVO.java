package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 阿里鱼用户配置响应视图对象。
 * <p>
 * 承载用户 ID 与配置内容，返回给前端展示。
 *
 * @author bixiu
 * @since 4.3.0
 */
@Data
@AutoMapper(target = AliyuUserConfigDTO.class)
public class AliyuUserConfigVO {

    /**
     * 系统用户 ID
     */
    private Long userId;

    /**
     * 阿里鱼用户配置（会话凭证束）
     */
    private AliyuUserConfigBO config;
}
