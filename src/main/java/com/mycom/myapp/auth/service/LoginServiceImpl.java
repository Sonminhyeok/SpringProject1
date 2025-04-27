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

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
