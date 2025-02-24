package com.example.NaengTulCook.service;

import com.example.NaengTulCook.dto.QnaCommentDTO;
import com.example.NaengTulCook.entity.QnaComment;
import com.example.NaengTulCook.repository.QnaCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnaCommentService {

    private final QnaCommentRepository qnaCommentRepository;

    public QnaCommentService(QnaCommentRepository qnaCommentRepository) {
        this.qnaCommentRepository = qnaCommentRepository;
    }

    // 특정 QnA 게시글의 댓글 조회
    public List<QnaCommentDTO> getCommentsByPostId(int postId) {
        return qnaCommentRepository.findByQnaPostIdWithUser(postId).stream()
                .map(QnaCommentDTO::new)
                .collect(Collectors.toList());
    }
}