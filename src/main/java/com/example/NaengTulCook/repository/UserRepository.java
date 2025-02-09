package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserIdentifier(String userIdentifier);
}
