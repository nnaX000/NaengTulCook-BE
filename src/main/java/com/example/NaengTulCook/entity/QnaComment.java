package com.example.NaengTulCook.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qna_comments")
public class QnaComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private QnaPost qnaPost;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public QnaComment() {}

    public QnaComment(User user, QnaPost qnaPost, String content) {
        this.user = user;
        this.qnaPost = qnaPost;
        this.content = content;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public User getUser() { return user; }
    public QnaPost getQnaPost() { return qnaPost; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "QnaComment{" +
                "id=" + id +
                ", user=" + (user != null ? user.getNickname() : "null") +
                ", qnaPostId=" + (qnaPost != null ? qnaPost.getId() : "null") +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}