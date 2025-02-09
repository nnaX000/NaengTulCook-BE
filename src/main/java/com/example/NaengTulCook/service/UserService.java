package com.example.NaengTulCook.service;

import com.example.NaengTulCook.dto.UserSignUpRequest;
import com.example.NaengTulCook.entity.User;
import com.example.NaengTulCook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean isNicknameTaken(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public void updateNickname(Integer userId, String nickname) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setNickname(nickname);  // 닉네임 설정
        userRepository.save(user);  // 유저 정보 저장
    }

    public void updateAgeRange(Integer userId, int ageRange) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setAge(ageRange);  // 나이대 설정
        userRepository.save(user);  // 유저 정보 저장
    }

    public void updateFamily(Integer userId, String family) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFamily(family);  // 가구 유형 설정
        userRepository.save(user);  // 유저 정보 저장
    }

    public void updateSkill(Integer userId, int skill) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setSkill(skill);  // 요리 실력 설정
        userRepository.save(user);  // 유저 정보 저장
    }

    public void updateFavorite(Integer userId, List<String> favorite) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFavorite(favorite);  // 취향 설정
        userRepository.save(user);  // 유저 정보 저장
    }

    public void updateIngredient(Integer userId, List<String> ingredients) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIngredient(ingredients);  // 재료 설정
        userRepository.save(user);  // 유저 정보 저장
    }
}
