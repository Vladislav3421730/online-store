package com.example.webapp.repository;

import com.example.webapp.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();
    Optional<User> findById(Long id);
    User update(User user);

    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String number);
}
