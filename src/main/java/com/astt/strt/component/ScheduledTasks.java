package com.astt.strt.component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {

        List<User> terminatedUsers = repository.findAll()
                .stream()
                .filter(user -> !user.getIsNotBusy())
                .filter(user -> MINUTES.between(user.getTimeStamp(), LocalDate.now()) > 5)
                .map(user -> user.setIsNotBusy(Boolean.TRUE))
                .map(user -> user.setTimeStamp(LocalDate.now()))
                .collect(Collectors.toList());

        repository.saveAll(terminatedUsers);

        terminatedUsers.forEach(user -> log.info("User has been terminated after 5 minutes in use - " + user.getLogin()));
    }
}
