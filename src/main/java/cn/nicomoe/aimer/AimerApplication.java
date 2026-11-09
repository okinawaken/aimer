package cn.nicomoe.aimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import xyz.erupt.core.annotation.EruptScan;

@EruptScan
@EntityScan
@SpringBootApplication
public class AimerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AimerApplication.class, args);
    }

}