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

    public Integer login(String userIdentifier, String password) {
        User user = userRepository.findByUserIdentifier(userIdentifier);

        if (user != null) {
            // 암호화된 비밀번호와 사용자가 입력한 비밀번호를 비교
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user.getId(); // 로그인 성공시 id 반환
            }
        }
        return null; // 로그인 실패시 null 반환
    }
}
