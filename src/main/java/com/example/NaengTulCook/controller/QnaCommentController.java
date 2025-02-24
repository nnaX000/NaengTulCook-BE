package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.dto.QnaCommentDTO;
import com.example.NaengTulCook.service.QnaCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna/comments")
public class QnaCommentController {

    private final QnaCommentService commentService;

    public QnaCommentController(QnaCommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "특정 QnA 게시글의 댓글 조회", description = "게시글 ID로 해당 게시글의 모든 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없음")
    })
    @GetMapping("/{postId}")
    public List<QnaCommentDTO> getCommentsByPostId(@PathVariable int postId) {
        return commentService.getCommentsByPostId(postId);
    }
}