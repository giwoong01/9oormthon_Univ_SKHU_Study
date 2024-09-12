package com.example.goormthonloginstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GoormthonLoginStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoormthonLoginStudyApplication.class, args);
    }

}
