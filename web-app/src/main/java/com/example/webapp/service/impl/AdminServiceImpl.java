package com.example.webapp.service.impl;

import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.service.AdminService;
import com.example.webapp.service.UserService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final static AdminServiceImpl INSTANCE = new AdminServiceImpl();
    public static AdminService getInstance(){
        return INSTANCE;
    }

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    @Transactional
    public void bun(User user) {
        log.info("{} {}", user.isBun() ? "ban user" : "unban",user.getEmail());
        user.setBun(!user.isBun());
        userService.update(user);
    }

    @Override
    @Transactional
    public void madeManager(User user) {
        if (!user.getRoleSet().add(Role.ROLE_MANAGER)) {
            log.info("User {} already has ROLE_MANAGER. Removing the role.", user.getUsername());
            user.getRoleSet().remove(Role.ROLE_MANAGER);
        }
        else {
            log.info("Adding ROLE_MANAGER to user {}.", user.getUsername());
        }
        userService.update(user);
        log.info("User {} has been updated successfully.", user.getUsername());

    }


}
