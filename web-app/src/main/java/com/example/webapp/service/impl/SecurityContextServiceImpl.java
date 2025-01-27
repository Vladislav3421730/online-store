package com.example.webapp.service.impl;

import com.example.webapp.dto.UserDto;
import com.example.webapp.exception.UserNotFoundException;
import com.example.webapp.mapper.UserMapper;
import com.example.webapp.model.User;
import com.example.webapp.service.SecurityContextService;
import com.example.webapp.wrappers.UserDetailsWrapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityContextServiceImpl implements SecurityContextService {

    UserMapper userMapper;

    @Override
    public UserDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsWrapper userDetailsWrapper = (UserDetailsWrapper) authentication.getPrincipal();
            User user = userDetailsWrapper.getUser();
            return userMapper.toDTO(user);
        }
        throw new UserNotFoundException("User wasn't found in Context");
    }

    @Override
    public void updateContext(UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userMapper.toEntity(userDto);
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                    new UserDetailsWrapper(user),
                    authentication.getCredentials(),
                    authentication.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        } else {
            throw new UserNotFoundException("User wasn't found in Context");
        }
    }
}
