package com.example.webapp.service.impl;

import com.example.webapp.dto.UserDto;
import com.example.webapp.mapper.UserMapper;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.service.AdminService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminServiceImpl implements AdminService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    @Transactional
    public void bun(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        log.info("{} {}", user.isBun() ? "ban user" : "unban", user.getEmail());
        user.setBun(!userDto.isBun());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void madeManager(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        if (!user.getRoleSet().add(Role.ROLE_MANAGER)) {
            log.info("User {} already has ROLE_MANAGER. Removing the role.", user.getUsername());
            user.getRoleSet().remove(Role.ROLE_MANAGER);
        } else {
            log.info("Adding ROLE_MANAGER to user {}.", user.getUsername());
        }
        userRepository.save(user);
        log.info("User {} has been updated successfully.", user.getUsername());

    }


}
