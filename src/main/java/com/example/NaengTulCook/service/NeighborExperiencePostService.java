package com.example.NaengTulCook.service;

import com.example.NaengTulCook.entity.NeighborExperiencePost;
import com.example.NaengTulCook.repository.NeighborExperiencePostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NeighborExperiencePostService {

    private final NeighborExperiencePostRepository postRepository;

    // ✅ Lombok 없이 생성자 직접 추가
    public NeighborExperiencePostService(NeighborExperiencePostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 📌 최신 게시글 목록 조회 (무한 스크롤 적용)
     * - 최신순 정렬하여 size 만큼 가져오기
     */
    public List<NeighborExperiencePost> getPosts(int page, int size) {
        Page<NeighborExperiencePost> postsPage =
                postRepository.findAllByOrderByIdDesc(PageRequest.of(page, size)); // ✅ 최신순 정렬
        return postsPage.getContent();
    }

    /**
     * 📌 특정 게시글 조회 (게시글 클릭 시 상세 정보 조회)
     */
    public NeighborExperiencePost getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    /**
     * 📌 새 게시글 작성 (ERD에 맞춰 필드 수정)
     */
    public NeighborExperiencePost createPost(NeighborExperiencePost post) {
        return postRepository.save(post);
    }
}