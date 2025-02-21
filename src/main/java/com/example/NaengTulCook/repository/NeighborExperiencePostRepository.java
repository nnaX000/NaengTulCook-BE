package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.NeighborExperiencePost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NeighborExperiencePostRepository extends JpaRepository<NeighborExperiencePost, Integer> {
    List<NeighborExperiencePost> findAllByOrderByCreatedAtDesc();
    List<NeighborExperiencePost> findAllByOrderByLikeCountDesc();
}