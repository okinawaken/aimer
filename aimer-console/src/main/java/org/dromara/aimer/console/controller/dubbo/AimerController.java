package org.dromara.aimer.console.controller.dubbo;

import org.dromara.aimer.integration.aimer.GreetingDubboRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bixiu
 */
@RestController
@RequestMapping("/dubbo")
public class AimerController {

    @Autowired
    private GreetingDubboRpcService greetingDubboRpcService;

    @GetMapping("/say_hello")
    public String sayHello(@RequestParam(value = "name") String name) {
        return greetingDubboRpcService.sayHello(name);
    }

    @GetMapping("/say_hi")
    public String sayHi(@RequestParam(value = "name") String name) {
        return greetingDubboRpcService.sayHi(name);
    }
}
