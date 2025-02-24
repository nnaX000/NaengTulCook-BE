package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.QnaLike;
import com.example.NaengTulCook.entity.QnaPost;
import com.example.NaengTulCook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnaPostLikeRepository extends JpaRepository<QnaLike, Integer> {
    Optional<QnaLike> findByUserAndQnaPost(User user, QnaPost qnaPost);
    boolean existsByUserIdAndQnaPostId(int userId, int qnaPostId);
}