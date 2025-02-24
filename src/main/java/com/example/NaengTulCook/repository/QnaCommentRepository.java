package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Integer> {
    @Query("SELECT c FROM QnaComment c JOIN FETCH c.user WHERE c.qnaPost.id = :qnaPostId")
    List<QnaComment> findByQnaPostIdWithUser(@Param("qnaPostId") int qnaPostId);

    int countByQnaPostId(int qnaPostId);
}