package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;

/**
 * 阿里鱼 CSRF token 域服务，返回已解包的业务数据。
 */
public interface AliyuCsrfService {

    /**
     * 获取 CSRF token 及服务端下发的会话标识。
     */
    AliyuCsrfTokenRespDTO getCsrfToken();
}
