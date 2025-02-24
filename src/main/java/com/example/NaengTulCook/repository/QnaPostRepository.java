package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.QnaPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QnaPostRepository extends JpaRepository<QnaPost, Integer> {
    List<QnaPost> findAllByOrderByCreatedAtDesc();  // 최신순 조회
    List<QnaPost> findAllByOrderByLikeCountDesc();  // 인기순 조회
}