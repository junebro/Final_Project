package com.final_project.Service;

import com.final_project.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void changePassword(String memno, String oldPassword, String mempw) {
        String currentPassword = userMapper.getPasswordById(memno);

        if (passwordEncoder.matches(oldPassword, currentPassword)) {
            userMapper.updatePassword(memno, passwordEncoder.encode(mempw));
        } else {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }
}