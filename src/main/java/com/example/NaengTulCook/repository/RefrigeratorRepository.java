package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.Recipe;
import com.example.NaengTulCook.entity.Refrigerator;
import com.example.NaengTulCook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {
    Refrigerator findByUserId(int userId); // 사용자 ID로 냉장고 데이터 조회

    @Query("SELECT r.ingredient FROM Refrigerator r WHERE r.user = :user")
    List<String> findIngredientsByUser(User user);
}
