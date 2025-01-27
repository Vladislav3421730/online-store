package com.example.webapp.service;

import com.example.webapp.dto.UserDto;

public interface SecurityContextService {

    UserDto getUser();
    void updateContext(UserDto userDto);

}
