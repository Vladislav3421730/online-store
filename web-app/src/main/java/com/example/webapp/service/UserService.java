package com.example.webapp.service;

import com.example.webapp.model.User;

import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> findByEmail(String email);
    User findById(Long id);
    User update(User user);

}
