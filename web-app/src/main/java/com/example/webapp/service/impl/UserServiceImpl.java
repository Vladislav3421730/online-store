package com.example.webapp.service.impl;

import com.example.webapp.dao.UserDao;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.service.UserService;
import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    private final static UserServiceImpl INSTANCE=new UserServiceImpl();
    public static UserServiceImpl getInstance(){
        return INSTANCE;
    }

    private final UserDao userDao= UserDao.getInstance();
    private final ValidatorFactory factory = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Override
    @Transactional
    public void save(@Valid User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
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
    public User update(@Valid User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return userDao.update(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
