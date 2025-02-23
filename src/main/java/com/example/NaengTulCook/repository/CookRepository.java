package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.Cook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CookRepository extends JpaRepository<Cook, Integer> {
    Optional<Cook> findTopByUserIdOrderByIdDesc(int userId);
}
