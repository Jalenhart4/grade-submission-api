package com.ltp.gradesubmission.service;


import com.ltp.gradesubmission.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface UserService {
    BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
    User getUser(Long id);
    User getUser(String username);
    User saveUser(User user);

}