package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {
    Refrigerator findByUserId(int userId); // 사용자 ID로 냉장고 데이터 조회
}
