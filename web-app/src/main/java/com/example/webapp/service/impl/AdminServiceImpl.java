package com.example.webapp.service.impl;

import com.example.webapp.dao.impl.UserDaoImpl;
import com.example.webapp.dto.UserDto;
import com.example.webapp.mapper.UserMapper;
import com.example.webapp.mapper.UserMapperImpl;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.service.AdminService;
import com.example.webapp.service.UserService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final static AdminServiceImpl INSTANCE = new AdminServiceImpl();

    public static AdminService getInstance() {
        return INSTANCE;
    }

    UserDaoImpl userDao = UserDaoImpl.getInstance();
    UserMapper userMapper = new UserMapperImpl();

    @Override
    @Transactional
    public void bun(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        log.info("{} {}", user.isBun() ? "ban user" : "unban", user.getEmail());
        user.setBun(!user.isBun());
        userDao.bunUser(user.getId());
    }

    @Override
    @Transactional
    public void madeManager(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        if (!user.getRoleSet().add(Role.ROLE_MANAGER)) {
            log.info("User {} already has ROLE_MANAGER. Removing the role.", user.getUsername());
            user.getRoleSet().remove(Role.ROLE_MANAGER);
            userDao.removeRoleManagerFromUser(user.getId());
        } else {
            userDao.addRoleManagerToUser(user.getId());
            log.info("Adding ROLE_MANAGER to user {}.", user.getUsername());
        }
        log.info("User {} has been updated successfully.", user.getUsername());

    }


}
