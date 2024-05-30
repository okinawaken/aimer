package org.dromara.aimer.integration.dubbo.aimer;

import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.api.dubbo.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GreetingDubboRpcService {

    @Autowired
    private GreetingService greetingDubboService;

    public String sayHello(String name) {
        String result;
        try {
            result = greetingDubboService.sayHello(name);
            log.info("GreetingDubboRpcService sayHello, name:{}, result:{}", name, result);
            return result;
        } catch (Exception e) {
            log.error("GreetingDubboRpcService sayHello, name:{}", name, e);
            return null;
        }
    }
}
