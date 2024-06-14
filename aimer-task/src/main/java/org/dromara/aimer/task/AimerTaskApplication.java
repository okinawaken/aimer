package org.dromara.aimer.task;

import org.dromara.aimer.service.AimerServiceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AimerServiceContext.class)
@SpringBootApplication
public class AimerTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(AimerTaskApplication.class, args);
    }

}
