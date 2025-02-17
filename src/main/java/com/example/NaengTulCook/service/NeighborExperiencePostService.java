package com.example.NaengTulCook.service;

import com.example.NaengTulCook.dto.NeighborExperiencePostDTO;
import com.example.NaengTulCook.entity.NeighborExperiencePost;
import com.example.NaengTulCook.repository.NeighborExperiencePostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NeighborExperiencePostService {

    private final NeighborExperiencePostRepository postRepository;

    public NeighborExperiencePostService(NeighborExperiencePostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 📌 게시글 생성 API
     */
    @Transactional
    public NeighborExperiencePost createPost(NeighborExperiencePostDTO dto) {
        // 🔹 새로운 게시글 생성
        NeighborExperiencePost post = new NeighborExperiencePost(
                dto.getUserId(), dto.getTitle(), dto.getContent()
        );

        // 🔹 게시글 저장
        return postRepository.save(post);
    }
}