package org.dromara.aimer.integration.dubbo.aimer;

import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.api.dubbo.IGreetingRpcService;
import org.dromara.aimer.common.exception.RpcException;
import org.dromara.aimer.common.response.BaseResponse;
import org.dromara.aimer.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author bixiu
 */
@Slf4j
@Service
public class GreetingDubboRpcService {

    @Autowired
    private IGreetingRpcService greetingRpcService;

    public String sayHello(String name) {
        try {
            BaseResponse<String> response = greetingRpcService.sayHello(name);
            log.info("GreetingDubboRpcService sayHello, name:{}, response:{}", name, JsonUtils.toJson(response));
            if (Objects.isNull(response)
                    || Objects.isNull(response.getData())
                    || BaseResponse.isError(response)) {
                log.error("GreetingDubboRpcService sayHello, name:{}, response has error", name, new RpcException("response has error"));
                return null;
            }
            return response.getData();
        } catch (Exception e) {
            log.error("GreetingDubboRpcService sayHello, name:{}", name, new RpcException(e));
            return null;
        }
    }
}
