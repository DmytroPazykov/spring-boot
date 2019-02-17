package com.astt.strt.controller;

import com.astt.strt.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Integer> {
}
