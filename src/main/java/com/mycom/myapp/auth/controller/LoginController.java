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

    /**
     * Constructs a LoginController with the specified LoginService.
     *
     * @param loginService the service handling authentication and user management logic
     */
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Handles user login requests by authenticating credentials and managing session state.
     *
     * Accepts a user login request, attempts authentication via the login service, and, if successful, stores the user's email in the session and returns user details with a success result. If authentication fails, returns a failure result with HTTP 401 Unauthorized.
     *
     * @param requestDto the login credentials provided by the user
     * @param session the HTTP session used to store user information upon successful login
     * @return a response entity containing a result ("success" or "fail") and, on success, user details; returns HTTP 200 on success or HTTP 401 on failure
     */
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

    /**
     * Handles user registration requests.
     *
     * Accepts user registration details and attempts to create a new user account. Returns a JSON response indicating success or failure, with HTTP 200 on success and HTTP 400 Bad Request on failure.
     *
     * @param requestDto the registration details for the new user
     * @return a response entity containing a result map with "success" or "fail"
     */
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

    /**
     * Handles HTTP PUT requests to update a user's profile information.
     *
     * @param requestDto the user profile update data
     * @return a response entity containing "result": "success" if the update was successful, or "result": "fail" with HTTP 400 if not
     */
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
