package com.example.demo.payrole.test;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayrollApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Tag("demo")
    public void contextLoads() {
        log.info("Starting test");
        log.info("Starting test");
        String message = restTemplate
            .getForObject("/employees/1", String.class);

        log.info("Doing Assert");

        assertEquals("{\"id\":1,\"name\":\"Bilbo Baggins\",\"role\":\"burglar\"}", message);
    }

}
