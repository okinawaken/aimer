package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/** 阿里鱼用户配置的响应视图（用户 ID + 配置内容），原样返回配置数据，Cookie 不做加工。 */
@Data
@AutoMapper(target = AliyuUserConfigDTO.class)
public class AliyuUserConfigVO {

    /** 系统用户 ID */
    private Long userId;

    /** 阿里鱼用户配置（持久会话 Cookie） */
    private AliyuUserConfigDetailVO config;
}
