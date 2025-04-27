package com.mycom.myapp.auth.service;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.myapp.auth.util.PasswordUtil;
import com.mycom.myapp.user.dto.UserLoginRequestDto;
import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import com.mycom.myapp.user.dto.UserUpdateRequestDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.auth.repository.UserRepository;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    /**
     * Constructs a LoginServiceImpl with the specified UserRepository for user data operations.
     */
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user by verifying the provided email and password.
     *
     * @param requestDto the login request containing user email and password
     * @return an Optional containing the authenticated User if credentials are valid; otherwise, an empty Optional
     */
    @Override
    public Optional<User> login(UserLoginRequestDto requestDto) {
        Optional<User> optional = userRepository.findByUserEmail(requestDto.getUserEmail());

        if (optional.isPresent()) {
            User user = optional.get();
            try {
                boolean matched = PasswordUtil.verifyPassword(
                    requestDto.getUserPassword(),
                    user.getSalt(),
                    user.getUserPassword()
                );
                if (matched) {
                    return Optional.of(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * Registers a new user with the provided registration details.
     *
     * Checks for existing users with the same email. If none exist, creates a new user with a hashed password and salt, and saves it to the repository.
     *
     * @param requestDto the registration details including email, name, and password
     * @return {@code true} if registration is successful; {@code false} if the user already exists or an error occurs
     */
    @Override
    public boolean register(UserRegisterRequestDto requestDto) {
        if (userRepository.findByUserEmail(requestDto.getUserEmail()).isPresent()) {
            return false;
        }

        try {
            String salt = PasswordUtil.generateSalt();
            String hashedPassword = PasswordUtil.hashPassword(requestDto.getUserPassword(), salt);

            User newUser = new User();
            newUser.setUserEmail(requestDto.getUserEmail());
            newUser.setUserName(requestDto.getUserName());
            newUser.setSalt(salt);
            newUser.setUserPassword(hashedPassword);

            userRepository.save(newUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates a user's profile information, including name and password.
     *
     * @param requestDto contains the user ID, new name, and new password
     * @return {@code true} if the profile was successfully updated; {@code false} if the user is not found or an error occurs
     */
    @Override
    public boolean updateProfile(UserUpdateRequestDto requestDto) {
        Optional<User> optional = userRepository.findById(requestDto.getUserId());

        if (optional.isPresent()) {
            User user = optional.get();
            try {
                String salt = PasswordUtil.generateSalt();
                String hashedPassword = PasswordUtil.hashPassword(requestDto.getUserPassword(), salt);

                user.setUserName(requestDto.getUserName());
                user.setUserPassword(hashedPassword);
                user.setSalt(salt);

                userRepository.save(user);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }
}
