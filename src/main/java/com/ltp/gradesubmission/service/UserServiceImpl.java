package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user, 404L);
    }

    @Override
    public User saveUser(User user) {
        String password = generatePassword(user);
        user.setPassword(password);
        return userRepository.save(user);
    }

    private String generatePassword(User user){
        return bCryptPasswordEncoder.encode(user.getUsername());
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }


    
}
