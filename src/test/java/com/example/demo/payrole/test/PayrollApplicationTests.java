package com.example.demo.payrole.test;

import com.example.demo.payrole.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayrollApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Tag("demo")
    void contextLoads() {
        log.info("Starting test");
        Employee employee = restTemplate
                .getForObject("/employees/1", Employee.class);

        log.info("Doing Assert");

        Employee expectedOne = new Employee()
                .setName("Bilbo Baggins")
                .setRole("burglar");

        assertEquals(expectedOne, employee);
    }

}
