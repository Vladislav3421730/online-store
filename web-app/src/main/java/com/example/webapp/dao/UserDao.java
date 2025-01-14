package com.example.webapp.dao;

import com.example.webapp.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String number);

    void addRoleManagerToUser(Long userId);
    void removeRoleManagerFromUser(Long userId);
    void bunUser(Long userId);
}
