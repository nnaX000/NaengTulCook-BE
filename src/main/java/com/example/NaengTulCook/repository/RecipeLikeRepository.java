package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.RecipeLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {
    boolean existsByPostIdAndUserId(int postId, int userId);
}
