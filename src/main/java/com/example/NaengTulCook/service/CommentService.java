package com.example.NaengTulCook.service;

import com.example.NaengTulCook.dto.CommentDTO;
import com.example.NaengTulCook.entity.Comment;
import com.example.NaengTulCook.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

//    public List<CommentDTO> getCommentsByPostId(int postId) {
//        List<Comment> comments = commentRepository.findByPostId(postId);
//        return comments.stream().map(CommentDTO::new).collect(Collectors.toList());
//    }
    public List<CommentDTO> getCommentsByPostId(int postId) {
        return commentRepository.findByPostIdWithUser(postId).stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }
}