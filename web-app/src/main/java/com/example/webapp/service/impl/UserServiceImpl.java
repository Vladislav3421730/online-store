package com.example.webapp.service.impl;

import com.example.webapp.dto.*;
import com.example.webapp.exception.UserNotFoundException;
import com.example.webapp.mapper.*;
import com.example.webapp.model.Cart;
import com.example.webapp.model.Order;
import com.example.webapp.dao.impl.UserDaoImpl;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.model.enums.Status;
import com.example.webapp.service.UserService;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.webapp.utils.HibernateValidatorUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final static UserServiceImpl INSTANCE = new UserServiceImpl();

    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    private final UserDaoImpl userDao = UserDaoImpl.getInstance();

    UserMapper userMapper = new UserMapperImpl();
    ProductMapper productMapper = new ProductMapperImpl();
    OrderMapper orderMapper = new OrderMapperImpl();

    @Override
    @Transactional
    public void save(RegisterUserDto registerUserDto) {
        User user = userMapper.toNewEntity(registerUserDto);
        Set<ConstraintViolation<User>> violations = HibernateValidatorUtil.getValidator().validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
        user.setRoleSet(Set.of(Role.ROLE_USER));
        userDao.save(user);
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> user = userDao.findByEmail(email);
        return user.map(userMapper::toDTO);
    }

    @Override
    public Optional<UserDto> findByPhoneNumber(String number) {
        Optional<User> user = userDao.findByPhoneNumber(number);
        return user.map(userMapper::toDTO);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userDao.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userDao.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public UserDto addProductToCart(UserDto userDto, ProductDto productDto) {

        User user = userMapper.toEntity(userDto);
        boolean isInCartList = user.getCarts().stream()
                .filter(cart -> cart.getProduct().getId().equals(productDto.getId()))
                .peek(cart -> cart.setAmount(cart.getAmount() + 1))
                .findFirst()
                .isPresent();
        if (!isInCartList) {
            log.info("a new item {} has been added to the user's {} cart", productDto.getTitle(), user.getEmail());
            Cart cart = new Cart(productMapper.toEntity(productDto));
            user.addCartToList(cart);
        }
        return update(user);
    }

    @Override
    @Transactional
    public UserDto makeOrder(UserDto userDto, OrderDto orderDto) {

        orderDto.setStatus(Status.ACCEPTED.getDisplayName());
        User user = userMapper.toEntity(userDto);
        Order order = orderMapper.toEntity(orderDto);

        order.setOrderItems(user.getCarts().stream()
                .map(cart -> OderItemCartMapper.map(cart, order))
                .toList());
        user.getCarts().clear();
        user.addOrderToList(order);
        return update(user);
    }

    @Transactional
    private UserDto update(@Valid User user) {
        Set<ConstraintViolation<User>> violations = HibernateValidatorUtil.getValidator().validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return userMapper.toDTO(userDao.update(user));
    }

}
