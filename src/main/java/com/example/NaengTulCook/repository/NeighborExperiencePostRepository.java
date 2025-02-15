package com.example.NaengTulCook.repository; // 해당 클래스가 속한 패키지

import com.example.NaengTulCook.entity.NeighborExperiencePost; // NeighborExperiencePost 엔티티 import
import org.springframework.data.domain.Page; // 페이징 처리를 위한 Page 객체 import
import org.springframework.data.domain.Pageable; // 페이징 처리를 위한 Pageable 객체 import
import org.springframework.data.jpa.repository.JpaRepository; // JpaRepository를 사용하기 위한 import
import org.springframework.stereotype.Repository; // Repository 어노테이션 import

@Repository // 해당 인터페이스가 Spring의 레파지토리(DAO) 역할을 한다고 명시
public interface NeighborExperiencePostRepository extends JpaRepository<NeighborExperiencePost, Long> {
    // JpaRepository를 상속받아 기본적인 CRUD 기능을 제공하며, NeighborExperiencePost 엔티티를 다룸

    // 최신 글을 ID 역순으로 조회하는 메서드 (페이징 지원)
    Page<NeighborExperiencePost> findAllByOrderByIdDesc(Pageable pageable);
}