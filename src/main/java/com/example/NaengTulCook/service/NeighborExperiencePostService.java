package com.example.NaengTulCook.service; // 해당 클래스가 속한 패키지

import com.example.NaengTulCook.entity.NeighborExperiencePost; // NeighborExperiencePost 엔티티 import
import com.example.NaengTulCook.repository.NeighborExperiencePostRepository; // Repository import
import org.springframework.data.domain.Page; // 페이징 처리를 위한 Page 객체 import
import org.springframework.data.domain.Pageable; // 페이징 처리를 위한 Pageable 객체 import
import org.springframework.stereotype.Service; // Service 어노테이션 import

@Service // 해당 클래스가 비즈니스 로직을 담당하는 서비스 클래스임을 명시
public class NeighborExperiencePostService {

    private final NeighborExperiencePostRepository neighborExperiencePostRepository;

    // 생성자를 직접 정의하여 Repository 주입
    public NeighborExperiencePostService(NeighborExperiencePostRepository neighborExperiencePostRepository) {
        this.neighborExperiencePostRepository = neighborExperiencePostRepository;
    }

    // 최신 글을 페이징 처리하여 가져오는 서비스 메서드
    public Page<NeighborExperiencePost> getRecentPosts(Pageable pageable) {
        return neighborExperiencePostRepository.findAllByOrderByIdDesc(pageable);
    }
}