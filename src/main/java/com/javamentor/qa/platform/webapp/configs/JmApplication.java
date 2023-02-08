package com.javamentor.qa.platform.webapp.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("com.javamentor.qa.platform")
@EntityScan("com.javamentor.qa.platform.models.entity")
public class JmApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmApplication.class, args);
    }

}
