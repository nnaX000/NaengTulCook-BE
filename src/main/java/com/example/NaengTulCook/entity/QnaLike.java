package com.example.NaengTulCook.entity;

import jakarta.persistence.*;

@Entity
//@Table(name = "qna_likes")
public class QnaLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private QnaPost qnaPost;

    @Column(nullable = false)
    private boolean isLiked;

    public QnaLike() {}

    public QnaLike(User user, QnaPost qnaPost, boolean isLiked) {
        this.user = user;
        this.qnaPost = qnaPost;
        this.isLiked = false;
    }

    public int getId() { return id; }
    public User getUser() { return user; }
    public QnaPost getQnaPost() { return qnaPost; }
//    public boolean isLiked() { return isLiked; }
//
//    public void setLiked(boolean liked) { isLiked = liked; }
}