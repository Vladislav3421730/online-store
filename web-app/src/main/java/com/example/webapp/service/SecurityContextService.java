package com.example.webapp.service;

import com.example.webapp.dto.UserDto;

public interface SecurityContextService {

    UserDto getUser();
    UserDto updateContext(UserDto userDto);

}
