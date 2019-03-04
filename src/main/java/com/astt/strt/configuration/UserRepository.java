package com.astt.strt.configuration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astt.strt.data.model.Role;
import com.astt.strt.data.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByRoleAndIsNotBusy(Role role, Boolean flag);

    User findByLogin(String login);

    List<User> findByIsNotBusyFalse();
}
