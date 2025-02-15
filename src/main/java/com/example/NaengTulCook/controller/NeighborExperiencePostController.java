package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.entity.NeighborExperiencePost;
import com.example.NaengTulCook.service.NeighborExperiencePostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/neighbor-experience/posts")
public class NeighborExperiencePostController {

    private final NeighborExperiencePostService postService;

    // ✅ Lombok 없이 생성자 추가
    public NeighborExperiencePostController(NeighborExperiencePostService postService) {
        this.postService = postService;
    }

    /**
     * 📌 최신 게시글 목록 조회 (무한 스크롤 적용)
     * - 최신순으로 정렬하여 size만큼 가져오기
     */
    @GetMapping
    public ResponseEntity<List<NeighborExperiencePost>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) { // ✅ 기본값: 10개씩 조회
        List<NeighborExperiencePost> posts = postService.getPosts(page, size);
        return ResponseEntity.ok(posts);
    }

    /**
     * 📌 특정 게시글 조회 (게시글 목록에서 클릭 시 상세 정보 조회)
     */
    @GetMapping("/{id}")
    public ResponseEntity<NeighborExperiencePost> getPostById(@PathVariable Long id) {
        NeighborExperiencePost post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    /**
     * 📌 새 게시글 작성 (ERD에 맞게 필드 수정)
     */
    @PostMapping
    public ResponseEntity<NeighborExperiencePost> createPost(@RequestBody NeighborExperiencePost post) {
        NeighborExperiencePost savedPost = postService.createPost(post);
        return ResponseEntity.ok(savedPost);
    }
}