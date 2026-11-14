package cn.nicomoe.aimer.notify.service;

import cn.nicomoe.aimer.notify.model.dto.BarkPushReqDTO;

/** Bark 推送服务：统一校验推送地址并调用 Bark 接口。 地址不合法或推送失败时只记日志，不向调用方抛出异常。 */
public interface BarkService {

    /** 向 {@code barkUrl} 推送一条 Bark 通知。 参数 barkUrl：完整推送地址，形如 {@code https://api.day.app/{key}} 参数 payload：请求体（title / body / group） 返回：是否推送成功，地址不合法或推送异常时返回 {@code false} */
    boolean push(String barkUrl, BarkPushReqDTO payload);
}
