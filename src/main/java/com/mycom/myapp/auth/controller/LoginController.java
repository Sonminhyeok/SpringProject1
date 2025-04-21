package com.mycom.myapp.auth.controller;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.UserDto;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDto userDto) {
        Optional<UserDto> optional = loginService.login(userDto);

        Map<String, String> map = new HashMap<>();
        if (optional.isPresent()) {
            UserDto user = optional.get();
            map.put("result", "success");
            map.put("userId", user.getUserEmail());
            return ResponseEntity.ok(map);
        } else {
            map.put("result", "fail");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> join(@RequestBody UserDto userDto) {
        boolean success = loginService.register(userDto);
        if (success) {
            return ResponseEntity.ok("회원가입 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 사용자입니다.");
        }
    }
    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestBody UserDto userDto) {
        boolean updated = loginService.updateProfile(userDto);
        return updated
            ? ResponseEntity.ok("프로필이 수정되었습니다.")
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정 실패");
    }
}