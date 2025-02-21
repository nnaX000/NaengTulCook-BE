package com.example.NaengTulCook.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NeighborExperiencePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 유저 정보 (닉네임 포함)

    private String title;
    private String content;
    private int likeCount = 0;
    private int viewCount = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ✅ 댓글 리스트 추가 (이 게시글에 달린 댓글들)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    // ✅ 기본 생성자 (JPA 필수)
    public NeighborExperiencePost() {
        this.likeCount = 0;
        this.viewCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public NeighborExperiencePost(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public User getUser() { return user; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getLikeCount() { return likeCount; }
    public int getViewCount() { return viewCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Comment> getComments() { return comments; }

    // ✅ Setter 메서드
    public void setUser(User user) { this.user = user; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    // ✅ 게시글 조회수 증가
    public void increaseViewCount() { this.viewCount++; }

    // ✅ 좋아요 증가 및 감소 메서드 추가
    public void increaseLikeCount() { this.likeCount++; }
    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

//    // ✅ 댓글 추가 메서드 (추가적인 기능)
//    public void addComment(Comment comment) {
//        this.comments.add(comment);
//        comment.setPost(this);
//    }
}