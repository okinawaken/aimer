package org.dromara.aimer.console;


import org.dromara.aimer.service.AimerServiceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AimerServiceContext.class)
@SpringBootApplication
public class AimerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AimerApplication.class, args);
    }

}
