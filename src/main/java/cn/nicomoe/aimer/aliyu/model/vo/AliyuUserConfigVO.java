package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserSettingConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 阿里鱼用户配置的响应视图（用户 ID + 配置内容），原样返回配置数据，会话凭证不做加工。
 */
@Data
@AutoMapper(target = AliyuUserConfigDTO.class)
public class AliyuUserConfigVO {

    /**
     * 系统用户 ID
     */
    private Long userId;

    /**
     * 阿里鱼用户配置（短时会话标识）
     */
    private AliyuUserConfigDetailVO config;

    /**
     * 阿里鱼用户配置明细的响应视图，原样返回配置数据，会话凭证不做加工。
     */
    @Data
    @AutoMapper(target = AliyuUserSettingConfigBO.class)
    public static class AliyuUserConfigDetailVO {

        /**
         * 短时会话标识（X-MZ-SESSION）
         */
        private String session;
    }
}
