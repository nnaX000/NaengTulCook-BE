//package com.example.NaengTulCook.controller;
//
//import com.example.NaengTulCook.dto.NeighborExperiencePostDTO;
//import com.example.NaengTulCook.entity.NeighborExperiencePost;
//import com.example.NaengTulCook.service.NeighborExperiencePostService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/neighbor-experience/posts")
//public class NeighborExperiencePostController {
//
//    private final NeighborExperiencePostService postService;
//
//    public NeighborExperiencePostController(NeighborExperiencePostService postService) {
//        this.postService = postService;
//    }
//
//    @Operation(summary = "게시글 등록", description = "새로운 이웃 경험 공유 게시글을 등록합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "게시글이 성공적으로 등록되었습니다."),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
//            @ApiResponse(responseCode = "500", description = "서버 오류입니다.")
//    })
//    @PostMapping
//    public ResponseEntity<NeighborExperiencePost> createPost(@RequestBody NeighborExperiencePostDTO dto) {
//        NeighborExperiencePost savedPost = postService.createPost(dto);
//        return ResponseEntity.status(201).body(savedPost);
//    }
//}

package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.dto.CommentDTO;
import com.example.NaengTulCook.dto.NeighborExperiencePostDTO;
import com.example.NaengTulCook.service.NeighborExperiencePostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/neighbor-experience/posts")
public class NeighborExperiencePostController {

    private final NeighborExperiencePostService postService;

    public NeighborExperiencePostController(NeighborExperiencePostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "게시글 등록", description = "새로운 이웃 경험 게시글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<NeighborExperiencePostDTO> createPost(
            @RequestBody NeighborExperiencePostDTO postDTO) {
        NeighborExperiencePostDTO savedPost = postService.createPost(postDTO);
        return ResponseEntity.ok(savedPost);
    }

    /**
     * 최신순 게시글 조회 API
     */
    @Operation(summary = "최신순 게시글 조회", description = "최신순으로 정렬된 게시글 리스트를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최신순 게시글 반환 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/posts/latest")
    public List<NeighborExperiencePostDTO> getLatestPosts(@RequestParam int userId) {
        return postService.getPostsSortedByLatest(userId);
    }

    /**
     * 인기순 게시글 조회 API
     */
    @Operation(summary = "인기순 게시글 조회", description = "좋아요 수가 많은 순으로 정렬된 게시글 리스트를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인기순 게시글 반환 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/posts/popular")
    public List<NeighborExperiencePostDTO> getPopularPosts(@RequestParam int userId) {
        return postService.getPostsSortedByLikes(userId);
    }

    /**
     * 특정 게시글 조회 (댓글 포함)
     */
    @Operation(summary = "특정 게시글 조회", description = "게시글 ID를 이용하여 해당 게시글의 상세 정보를 가져옵니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<NeighborExperiencePostDTO> getPostDetail(@PathVariable int postId, @RequestParam int userId) {
        return ResponseEntity.ok(postService.getPostDetail(postId, userId));
    }

    /**
     * 댓글 등록
     */
    @Operation(summary = "댓글 등록", description = "특정 게시글에 댓글을 추가")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable int postId, @RequestParam int userId, @RequestBody String content) {
        return ResponseEntity.ok(postService.addComment(postId, userId, content));
    }

    /**
     * 게시글 좋아요/좋아요 취소
     */
    @Operation(summary = "게시글 좋아요/취소", description = "게시글의 좋아요 상태를 토글함.")
    @PostMapping("/{postId}/like")
    public ResponseEntity<Boolean> toggleLike(@PathVariable int postId, @RequestParam int userId) {
        return ResponseEntity.ok(postService.toggleLike(postId, userId));
    }

}