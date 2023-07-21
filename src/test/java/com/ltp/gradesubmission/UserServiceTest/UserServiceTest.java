package com.ltp.gradesubmission.UserServiceTest;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.repository.UserRepository;
import com.ltp.gradesubmission.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    public UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Long userId;
    private User testUser;
    @Before
    public void init(){
        Long userId = 1L;
        User testUser = new User("testUser", "testUser$123");
    }

    @Test
    public void testSuccessfulGetUserById(){
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        Assert.assertEquals(userService.getUser(userId),testUser);
    }
    @Test
    public void testUnsuccessfulGetUserById(){
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        var error = Assertions.assertThrows(EntityNotFoundException.class, () -> {
                userService.getUser(userId);
        });
        Assert.assertEquals(error.getLocalizedMessage(), "The user with id '" + userId + "' does not exist in our records");
    }
    @Test
    public void testSuccessfulGetUserByUsername(){
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Assert.assertEquals(userService.getUser(userId).getUsername(),testUser.getUsername());
    }
    @Test
    public void testUnsuccessfulGetUserByUsername(){
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.empty());
        var error = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userService.getUser(testUser.getUsername());
        });
        Assert.assertEquals(error.getLocalizedMessage(), "The user with id '" + userId + "' does not exist in our records");
    }

    @Test
    public void testSaveUser(){
        User newUser = userService.saveUser(testUser);
        Assert.assertEquals(newUser.getPassword(), bCryptPasswordEncoder.encode(testUser.getPassword()));
    }
}
