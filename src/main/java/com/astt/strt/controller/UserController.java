package com.astt.strt.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.astt.strt.configuration.UserRepository;
import com.astt.strt.data.model.Role;
import com.astt.strt.data.model.User;
import com.astt.strt.exceptions.NoFreeUserByRoleException;
import com.astt.strt.exceptions.NoSuchRoleException;
import com.astt.strt.exceptions.NoSuchUserException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository repository;

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/user/reset")
    User resetUser(@RequestBody User user) {

        User userToBeReset = repository.findAll()
                .stream()
                .filter(el -> el.getLogin().equals(user.getLogin()))
                .findAny()
                .orElseThrow(() -> new NoSuchUserException(user.getLogin()));

        userToBeReset
                .setIsNotBusy(Boolean.TRUE)
                .setTimeStamp(LocalDate.now());

        return repository.save(userToBeReset);
    }

    @PostMapping("/user")
    User newUser(@RequestBody User user) {

        return repository.save(user);
    }

    @GetMapping("/user/role/{role}")
    User getUserByRole(@PathVariable String role) {

        Role parsedRole = Arrays.stream(Role.values())
                .filter(el -> el.name().equalsIgnoreCase(role))
                .findAny()
                .orElseThrow(() -> new NoSuchRoleException(role));

        User userToWorkWith = repository.findAll()
                .stream()
                .filter(employee -> employee.getRole().equals(parsedRole))
                .filter(User::getIsNotBusy)
                .findAny()
                .orElseThrow(() -> new NoFreeUserByRoleException(parsedRole));

        userToWorkWith
                .setIsNotBusy(Boolean.FALSE)
                .setTimeStamp(LocalDate.now());

        return repository.save(userToWorkWith);
    }
}
