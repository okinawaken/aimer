package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.AliyuCsrfClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuCsrfTokenRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuCsrfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 阿里鱼 CSRF token 域服务实现。
 */
@Service
@RequiredArgsConstructor
public class AliyuCsrfServiceImpl implements AliyuCsrfService {

    private final AliyuCsrfClient csrfClient;

    @Override
    public AliyuCsrfTokenRespDTO getCsrfToken() {
        AliyuClientResult<AliyuCsrfTokenRespDTO> result = csrfClient.getCsrfToken();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
