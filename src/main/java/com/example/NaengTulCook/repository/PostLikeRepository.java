package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.PostLike;
import com.example.NaengTulCook.entity.NeighborExperiencePost;
import com.example.NaengTulCook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    Optional<PostLike> findByUserAndPost(User user, NeighborExperiencePost post);
    boolean existsByUserIdAndPostId(int userId, int postId);
}