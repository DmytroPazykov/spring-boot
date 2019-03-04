package com.astt.strt.component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.astt.strt.configuration.UserRepository;
import com.astt.strt.data.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.time.temporal.ChronoUnit.MINUTES;

@Component
@Slf4j
@AllArgsConstructor
public class ScheduledTasks {

    private final UserRepository repository;

    @Scheduled(cron = "${clean.cron.time}")
    public void reportCurrentTime() {

        log.info("Scheduled procedure has been started");

        List<User> terminatedUsers = repository.findByIsNotBusyFalse()
                .stream()
                .filter(user -> MINUTES.between(user.getTimeStamp(), LocalDateTime.now()) > 0)
                .map(user -> user.setIsNotBusy(Boolean.TRUE))
                .map(user -> user.setTimeStamp(LocalDateTime.now()))
                .collect(Collectors.toList());

        repository.saveAll(terminatedUsers);

        terminatedUsers.forEach(user -> log.info("User has been terminated after 5 minutes in use - " + user.getLogin()));
        log.info("Scheduled procedure has been finished");
    }
}
