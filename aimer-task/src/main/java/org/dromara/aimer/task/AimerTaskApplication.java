package org.dromara.aimer.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import tech.powerjob.server.PowerJobServerApplication;

/**
 * @author bixiu
 */
@Import(PowerJobServerApplication.class)
@SpringBootApplication
public class AimerTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(AimerTaskApplication.class, args);
    }

}
