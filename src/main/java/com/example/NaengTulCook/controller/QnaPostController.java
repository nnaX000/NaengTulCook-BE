package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.dto.QnaCommentDTO;
import com.example.NaengTulCook.dto.QnaPostDTO;
import com.example.NaengTulCook.service.QnaPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna/posts")
public class QnaPostController {

    private final QnaPostService qnaPostService;

    public QnaPostController(QnaPostService qnaPostService) {
        this.qnaPostService = qnaPostService;
    }

    @Operation(summary = "QnA 게시글 등록", description = "새로운 QnA 게시글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<QnaPostDTO> createPost(@RequestBody QnaPostDTO postDTO) {
        QnaPostDTO savedPost = qnaPostService.createPost(postDTO);
        return ResponseEntity.ok(savedPost);
    }

    /**
     * 최신순 QnA 게시글 조회 API
     */
    @Operation(summary = "최신순 QnA 게시글 조회", description = "최신순으로 정렬된 게시글 리스트를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최신순 게시글 반환 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/latest")
    public List<QnaPostDTO> getLatestPosts(@RequestParam int userId) {
        return qnaPostService.getPostsSortedByLatest(userId);
    }

    /**
     * 인기순 QnA 게시글 조회 API
     */
    @Operation(summary = "인기순 QnA 게시글 조회", description = "좋아요 수가 많은 순으로 정렬된 게시글 리스트를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인기순 게시글 반환 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/popular")
    public List<QnaPostDTO> getPopularPosts(@RequestParam int userId) {
        return qnaPostService.getPostsSortedByLikes(userId);
    }

    /**
     * 특정 QnA 게시글 조회 (댓글 포함)
     */
    @Operation(summary = "특정 QnA 게시글 조회", description = "게시글 ID를 이용하여 해당 게시글의 상세 정보를 가져옵니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<QnaPostDTO> getPostDetail(@PathVariable int postId, @RequestParam int userId) {
        return ResponseEntity.ok(qnaPostService.getPostDetail(postId, userId));
    }

    /**
     * QnA 댓글 등록
     */
    @Operation(summary = "QnA 댓글 등록", description = "특정 QnA 게시글에 댓글을 추가합니다.")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<QnaCommentDTO> addComment(@PathVariable int postId, @RequestParam int userId, @RequestBody String content) {
        return ResponseEntity.ok(qnaPostService.addComment(postId, userId, content));
    }

    /**
     * QnA 게시글 좋아요/좋아요 취소
     */
    @Operation(summary = "QnA 게시글 좋아요/취소", description = "게시글의 좋아요 상태를 토글합니다.")
    @PostMapping("/{postId}/like")
    public ResponseEntity<Boolean> toggleLike(@PathVariable int postId, @RequestParam int userId) {
        return ResponseEntity.ok(qnaPostService.toggleLike(postId, userId));
    }
}