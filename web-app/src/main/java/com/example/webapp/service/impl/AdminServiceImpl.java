package com.example.webapp.service.impl;

import com.example.webapp.dto.UserDto;
import com.example.webapp.mapper.UserMapper;
import com.example.webapp.mapper.UserMapperImpl;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.service.AdminService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    UserMapper userMapper = new UserMapperImpl();

    @Override
    public void bun(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        log.info("{} {}", user.isBun() ? "ban user" : "unban", user.getEmail());
        user.setBun(!user.isBun());
        userRepository.save(user);
    }

    @Override
    public void madeManager(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        if (!user.getRoleSet().add(Role.ROLE_MANAGER)) {
            log.info("User {} already has ROLE_MANAGER. Removing the role.", user.getUsername());
            user.getRoleSet().remove(Role.ROLE_MANAGER);
            userRepository.save(user);
        } else {
            userRepository.save(user);
            log.info("Adding ROLE_MANAGER to user {}.", user.getUsername());
        }
        log.info("User {} has been updated successfully.", user.getUsername());

    }


}
