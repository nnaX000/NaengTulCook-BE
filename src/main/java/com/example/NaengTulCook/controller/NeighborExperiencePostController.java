package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.dto.NeighborExperiencePostDTO;
import com.example.NaengTulCook.entity.NeighborExperiencePost;
import com.example.NaengTulCook.service.NeighborExperiencePostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/neighbor-experience/posts")
public class NeighborExperiencePostController {

    private final NeighborExperiencePostService postService;

    public NeighborExperiencePostController(NeighborExperiencePostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "게시글 등록", description = "새로운 이웃 경험 공유 게시글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류입니다.")
    })
    @PostMapping
    public ResponseEntity<NeighborExperiencePost> createPost(@RequestBody NeighborExperiencePostDTO dto) {
        NeighborExperiencePost savedPost = postService.createPost(dto);
        return ResponseEntity.status(201).body(savedPost);
    }
}