package com.mycom.myapp.auth.service;

import java.util.Optional;
import com.mycom.myapp.user.dto.UserDto;

public interface LoginService {
    Optional<UserDto> login(UserDto userDto);
    boolean register(UserDto userDto);
	boolean updateProfile(UserDto userDto);
}