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

            if (passwordEncoder.matches(password, user.getPassword())) {
                return user.getId();
            }
        }
        return null;
    }

    public boolean isNicknameTaken(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public void updateNickname(Integer userId, String nickname) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setNickname(nickname);
        userRepository.save(user);
    }

    public void updateAgeRange(Integer userId, int ageRange) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setAge(ageRange);
        userRepository.save(user);
    }

    public void updateFamily(Integer userId, String family) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFamily(family);
        userRepository.save(user);
    }

    public void updateSkill(Integer userId, int skill) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setSkill(skill);
        userRepository.save(user);
    }

    public void updateFavorite(Integer userId, List<String> favorite) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFavorite(favorite);
        userRepository.save(user);
    }

    public void updateIngredient(Integer userId, List<String> ingredients) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIngredient(ingredients);
        userRepository.save(user);
    }
}
