package com.astt.strt.controller;

import com.astt.strt.data.model.Role;
import com.astt.strt.data.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initUserInformation(UserRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new User("AutoSuperAdmin", "Amstock1", "User Automation", Role.ADMIN)));
            log.info("Preloading " + repository.save(new User("DmytroPazykovSuper", "Amstock1", "User Automation", Role.ADMIN)));
            log.info("Preloading " + repository.save(new User("DmytroPazykov", "Amstock1", "User Automation", Role.USER)));
        };
    }
}
