package com.example.webapp.service.impl;

import com.example.webapp.dao.UserDao;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.service.UserService;
import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    private final static UserServiceImpl INSTANCE=new UserServiceImpl();
    public static UserServiceImpl getInstance(){
        return INSTANCE;
    }

    private final UserDao userDao= UserDao.getInstance();

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
        user.setRoleSet(Set.of(Role.ROLE_USER));
        userDao.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public User update(User user) {
        return userDao.update(user);
    }
}
