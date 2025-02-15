package com.example.NaengTulCook.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "neighbor_experience_post")
@Getter
@Setter
@NoArgsConstructor

public class NeighborExperiencePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 게시글 ID(고유값)

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계: 여러 개의 게시글이 하나의 유저를 참조
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자 정보 (User entity)

    @Column(nullable = false) //NOT NULL 제약 조건 추가
    private String title; // 게시글 제목

    @Column(columnDefinition = "TEXT", nullable = false) // 본문 긴 문자열 허용
    private String content; // 게시글 내용

    @Column(nullable = false)
    private int likeCount = 0; // 좋아요 수

    @Column(nullable = false)
    private int viewCount = 0; // 조회수

    @Column(length = 255)
    private String picture; // 이미지 URL

    // 게시글 등록 (생성자)
    public NeighborExperiencePost(User user, String title, String content, String picture) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.likeCount = 0;
        this.viewCount = 0;
    }

    // 조회수 증가 메서드
    public void incrementViewCount() {
        this.viewCount++;
    }

    // 좋아요 증가 메서드
    public void incrementLikeCount() {
        this.likeCount++;
    }
}
