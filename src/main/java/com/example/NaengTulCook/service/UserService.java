package com.example.NaengTulCook.service;

import com.example.NaengTulCook.dto.UserSignUpRequest;
import com.example.NaengTulCook.entity.User;
import com.example.NaengTulCook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User signUp(UserSignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setUserIdentifier(request.getUserIdentifier());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
}
