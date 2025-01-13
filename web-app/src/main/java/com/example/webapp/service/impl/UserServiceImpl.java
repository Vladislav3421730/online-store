package com.example.webapp.service.impl;

import com.example.webapp.dto.RegisterUserDto;
import com.example.webapp.exception.UserNotFoundException;
import com.example.webapp.mapper.OderItemCartMapper;
import com.example.webapp.mapper.UserMapper;
import com.example.webapp.mapper.UserMapperImpl;
import com.example.webapp.model.Cart;
import com.example.webapp.model.Order;
import com.example.webapp.model.Product;
import com.example.webapp.dao.UserDao;
import com.example.webapp.dao.impl.UserDaoImpl;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.service.UserService;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.webapp.utils.HibernateValidatorUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService {

    private final static UserServiceImpl INSTANCE=new UserServiceImpl();
    public static UserServiceImpl getInstance(){
        return INSTANCE;
    }

    private final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private final UserMapper userMapper = new UserMapperImpl();


    @Override
    @Transactional
    public void save(RegisterUserDto registerUserDto) {
        User user = userMapper.toEntity(registerUserDto);
        Set<ConstraintViolation<User>> violations = HibernateValidatorUtil.getValidator().validate(user);
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
    public Optional<User> findByPhoneNumber(String number) {
        return userDao.findByPhoneNumber(number);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElseThrow(()->
                new UserNotFoundException("User with id "+id+" was not found"));
    }

    @Override
    @Transactional
    public void update(@Valid User user) {
        Set<ConstraintViolation<User>> violations = HibernateValidatorUtil.getValidator().validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        update(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void addProductToCart(User user, Product product) {
        boolean isInCartList = user.getCarts().stream()
                .filter(cart -> cart.getProduct().getId().equals(product.getId()))
                .peek(cart -> cart.setAmount(cart.getAmount() + 1))
                .findFirst()
                .isPresent();
        if (!isInCartList) {
            log.info("a new item {} has been added to the user's {} cart",product.getTitle(),user.getEmail());
            Cart cart = new Cart(product);
            user.addCartToList(cart);
        }
        update(user);
    }

    @Override
    @Transactional
    public void makeOrder(User user, Order order) {
        order.setOrderItems(user.getCarts().stream()
                .map(cart -> OderItemCartMapper.map(cart, order))
                .toList());
        user.getCarts().clear();
        user.addOrderToList(order);
        update(user);
    }

}
