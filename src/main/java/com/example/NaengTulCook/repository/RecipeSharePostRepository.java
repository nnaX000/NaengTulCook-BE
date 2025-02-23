package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.RecipeSharePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeSharePostRepository extends JpaRepository<RecipeSharePost, Integer> {
    // 특정 family 유형을 가진 사용자들이 많이 스크랩한 게시글 TOP 5 조회
    @Query("""
            SELECT p FROM RecipeSharePost p
            JOIN RecipeLike rl ON p.id = rl.post.id
            JOIN User u ON rl.user.id = u.id
            WHERE u.family = :family
            GROUP BY p.id
            ORDER BY COUNT(rl.id) DESC
            LIMIT 5
            """)
    List<RecipeSharePost> findTop5ByFamily(String family);

    // 특정 age(나잇대) 유형을 가진 사용자들이 많이 스크랩한 게시글 TOP 5 조회
    @Query("""
            SELECT p FROM RecipeSharePost p
            JOIN RecipeLike rl ON p.id = rl.post.id
            JOIN User u ON rl.user.id = u.id
            WHERE u.age = :age
            GROUP BY p.id
            ORDER BY COUNT(rl.id) DESC
            LIMIT 5
            """)
    List<RecipeSharePost> findTop5ByAge(int age);
}