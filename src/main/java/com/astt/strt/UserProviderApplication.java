package com.astt.strt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UserProviderApplication {

    public static void main(String... args) {
        SpringApplication.run(UserProviderApplication.class, args);
    }
}
