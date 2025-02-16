package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.NeighborExperiencePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborExperiencePostRepository extends JpaRepository<NeighborExperiencePost, Integer> {
}
