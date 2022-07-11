package com.ssafy.queant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class QueantApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueantApplication.class, args);
    }

}
