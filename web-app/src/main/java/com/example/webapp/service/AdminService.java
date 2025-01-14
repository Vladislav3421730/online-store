package com.example.webapp.service;

import com.example.webapp.dto.UserDto;

public interface AdminService {
    void bun(UserDto userDto);
    void madeManager(UserDto userDto);
}
