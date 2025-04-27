package com.mycom.myapp.auth.service;

import java.util.Optional;

import com.mycom.myapp.user.dto.UserLoginRequestDto;
import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import com.mycom.myapp.user.dto.UserUpdateRequestDto;
import com.mycom.myapp.user.entity.User;

public interface LoginService {
    Optional<User> login(UserLoginRequestDto requestDto);
    boolean register(UserRegisterRequestDto requestDto);
    boolean updateProfile(UserUpdateRequestDto requestDto);
}
