package org.dromara.aimer.auth;

import org.dromara.aimer.integration.AimerIntegrationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author bixiu
 */
@Import(AimerIntegrationContext.class)
@SpringBootApplication
public class AimerAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AimerAuthApplication.class, args);
    }

}
