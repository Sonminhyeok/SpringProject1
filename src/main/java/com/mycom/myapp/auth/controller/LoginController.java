package com.mycom.myapp.auth.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.UserLoginRequestDto;
import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import com.mycom.myapp.user.dto.UserResponseDto;
import com.mycom.myapp.user.dto.UserUpdateRequestDto;
import com.mycom.myapp.user.entity.User;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequestDto requestDto, HttpSession session) {
        Optional<User> optional = loginService.login(requestDto);

        Map<String, Object> map = new HashMap<>();
        if (optional.isPresent()) 
        {
            User loggedInUser = optional.get();
            UserResponseDto responseDto = new UserResponseDto(
                loggedInUser.getUserId(),
                loggedInUser.getUserEmail(),
                loggedInUser.getUserName()
            );

            session.setAttribute("userEmail", loggedInUser.getUserEmail());

            map.put("result", "success");
            map.put("user", responseDto);
            return ResponseEntity.ok(map);
        } else {
            map.put("result", "fail");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRegisterRequestDto requestDto) {
        boolean success = loginService.register(requestDto);

        Map<String, String> map = new HashMap<>();
        if (success) 
        {
            map.put("result", "success");
            return ResponseEntity.ok(map);
        } else 
        {
            map.put("result", "fail");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<Map<String, String>> updateProfile(@RequestBody UserUpdateRequestDto requestDto) {
        boolean updated = loginService.updateProfile(requestDto);

        Map<String, String> map = new HashMap<>();
        if (updated) {
            map.put("result", "success");
            return ResponseEntity.ok(map);
        } else {
            map.put("result", "fail");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
    }
}
