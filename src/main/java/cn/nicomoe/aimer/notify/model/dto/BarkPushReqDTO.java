package cn.nicomoe.aimer.notify.model.dto;

import lombok.Data;

/**
 * Bark 推送请求体。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class BarkPushReqDTO {

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知正文
     */
    private String body;

    /**
     * 推送分组，便于在 Bark 客户端按来源聚合
     */
    private String group;
}
