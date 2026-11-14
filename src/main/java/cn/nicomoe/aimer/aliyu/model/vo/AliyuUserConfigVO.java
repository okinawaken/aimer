package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/** 阿里鱼用户配置的响应视图（用户 ID + 配置内容）。 */
@Data
@AutoMapper(target = AliyuUserConfigDTO.class)
public class AliyuUserConfigVO {

    /** 系统用户 ID */
    private Long userId;

    /** 阿里鱼用户配置（会话凭证束） */
    private AliyuUserConfigBO config;
}
