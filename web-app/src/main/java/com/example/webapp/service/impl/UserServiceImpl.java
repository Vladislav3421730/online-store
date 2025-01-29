package com.example.webapp.service.impl;

import com.example.webapp.dto.*;
import com.example.webapp.exception.UserNotFoundException;
import com.example.webapp.mapper.*;
import com.example.webapp.model.Address;
import com.example.webapp.model.Cart;
import com.example.webapp.model.Order;;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.model.enums.Status;
import com.example.webapp.repository.AddressRepository;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    AddressRepository addressRepository;

    PasswordEncoder passwordEncoder;

    OderItemCartMapper oderItemCartMapper;
    UserMapper userMapper;
    ProductMapper productMapper;
    OrderMapper orderMapper;

    @Override
    @Transactional
    public void save(RegisterUserDto registerUserDto) {
        log.info("Registering a new user: {}", registerUserDto.getEmail());

        User user = userMapper.toNewEntity(registerUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleSet(Set.of(Role.ROLE_USER));
        userRepository.save(user);

        log.info("User registered successfully: {}", registerUserDto.getEmail());
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMapper::toDTO);
    }

    @Override
    public Optional<UserDto> findByPhoneNumber(String number) {
        Optional<User> user = userRepository.findByPhoneNumber(number);
        return user.map(userMapper::toDTO);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " was not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findByOrderById().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public UserDto addProductToCart(UserDto userDto, ProductDto productDto) {
        log.info("Adding product to cart: {} for user: {}", productDto.getTitle(), userDto.getEmail());

        User user = userMapper.toEntity(userDto);
        boolean isInCartList = user.getCarts().stream()
                .filter(cart -> cart.getProduct().getId().equals(productDto.getId()))
                .peek(cart -> cart.setAmount(cart.getAmount() + 1))
                .findFirst()
                .isPresent();

        if (!isInCartList) {
            log.info("Adding a new item {} to the user's cart", productDto.getTitle());
            Cart cart = new Cart(productMapper.toEntity(productDto));
            user.addCartToList(cart);
        }

        return update(user);
    }

    @Override
    @Transactional
    public UserDto makeOrder(UserDto userDto, OrderDto orderDto) {
        log.info("Making an order for user: {}", userDto.getEmail());

        orderDto.setStatus(Status.ACCEPTED.getDisplayName());
        User user = userMapper.toEntity(userDto);
        Order order = orderMapper.toEntity(orderDto);
        log.info("address: {}", order.getAddress());

        List<Address> addresses = user.getOrders().stream()
                .map(Order::getAddress)
                .toList();

        if (!addresses.contains(order.getAddress())) {
            order.getAddress().setId(null);
            addressRepository.save(order.getAddress());
        }

        order.setOrderItems(user.getCarts().stream()
                .map(cart -> oderItemCartMapper.map(cart, order))
                .toList());
        user.getCarts().clear();
        user.addOrderToList(order);

        log.info("Order for user {} created successfully", userDto.getEmail());
        return update(user);
    }

    @Transactional
    private UserDto update(User user) {
        log.info("Updating user with id: {}", user.getId());
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with id: {}", updatedUser.getId());
        return userMapper.toDTO(updatedUser);
    }
}
