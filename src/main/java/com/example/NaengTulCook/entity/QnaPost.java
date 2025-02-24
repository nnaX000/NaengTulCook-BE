package com.example.NaengTulCook.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "qna_posts")
public class QnaPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 유저 정보 (닉네임 포함)

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int viewCount = 0;

    @Column(nullable = false)
    private int commentCount = 0;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    // ✅ QnA 댓글 리스트 추가
    @OneToMany(mappedBy = "qnaPost", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QnaComment> comments = new ArrayList<>();

    public QnaPost() {
        this.likeCount = 0;
        this.viewCount = 0;
    }

    public QnaPost(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public int getId() { return id; }
    public User getUser() { return user; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getLikeCount() { return likeCount; }
    public int getViewCount() { return viewCount; }
    public int getCommentCount() { return commentCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<QnaComment> getComments() { return comments; }

    public void setUser(User user) { this.user = user; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }

    public void increaseViewCount() { this.viewCount++; }
    public void increaseLikeCount() { this.likeCount++; }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void increaseCommentCount() { this.commentCount++; }

    public void decreaseCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }
}