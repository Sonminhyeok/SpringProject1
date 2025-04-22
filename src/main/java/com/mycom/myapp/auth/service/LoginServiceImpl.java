package com.mycom.myapp.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycom.myapp.auth.dao.LoginDao;
import com.mycom.myapp.auth.util.PasswordUtil;
import com.mycom.myapp.user.dto.UserDto;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;

    public LoginServiceImpl(LoginDao loginDao) {
        this.loginDao = loginDao;
    }
    @Override
    public Optional<UserDto> login(UserDto userDto) {
        System.out.println("▶ 로그인 시도 이메일: " + userDto.getUserEmail());
        System.out.println("▶ 입력 비밀번호: " + userDto.getUserPassword());

        UserDto stored = loginDao.login(userDto.getUserEmail());

        if (stored == null) {
            System.out.println("❌ 해당 이메일 사용자가 존재하지 않음");
            return Optional.empty();
        }

        System.out.println("▶ DB 저장된 해시된 비밀번호: " + stored.getUserPassword());
        System.out.println("▶ DB 저장된 salt: " + stored.getSalt());

        try {
            boolean matched = PasswordUtil.verifyPassword(
                    userDto.getUserPassword(),
                    stored.getSalt(),
                    stored.getUserPassword()
            );

            System.out.println("▶ 비밀번호 일치 여부: " + matched);

            if (matched) {
                return Optional.of(stored);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean register(UserDto userDto) {
    	
    	if (loginDao.login(userDto.getUserEmail()) != null) {
            return false;
        }

        try {
            String salt = PasswordUtil.generateSalt();
            String hashedPassword = PasswordUtil.hashPassword(userDto.getUserPassword(), salt);
            userDto.setUserPassword(hashedPassword);
            userDto.setSalt(salt); 
            
            loginDao.register(userDto);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateProfile(UserDto userDto) {
        try {
            UserDto existing = loginDao.login(userDto.getUserEmail());
            if (existing == null) {
                System.out.println("❌ 존재하지 않는 사용자");
                return false;
            }

            if (userDto.getUserPassword() == null || userDto.getUserPassword().isEmpty()) {
                System.out.println("❌ 현재 비밀번호가 비어 있음");
                return false;
            }

            boolean matched = PasswordUtil.verifyPassword(
                    userDto.getUserPassword(),      
                    existing.getSalt(),
                    existing.getUserPassword()
            );

            if (!matched) {
                System.out.println("❌ 현재 비밀번호 불일치");
                return false;
            }

          
            if (userDto.getNewPassword() != null && !userDto.getNewPassword().isEmpty()) {
                String newSalt = PasswordUtil.generateSalt();
                String newHashed = PasswordUtil.hashPassword(userDto.getNewPassword(), newSalt);
                userDto.setSalt(newSalt);
                userDto.setUserPassword(newHashed);
            } else {
                userDto.setSalt(existing.getSalt());
                userDto.setUserPassword(existing.getUserPassword());
            }

            loginDao.updateProfile(userDto);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



	
}