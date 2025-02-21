package com.example.NaengTulCook.entity;

import jakarta.persistence.*;

@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private NeighborExperiencePost post;

    @Column(nullable = false)
    private boolean isLiked;

    public PostLike() {}

    public PostLike(User user, NeighborExperiencePost post, boolean isLiked) {
        this.user = user;
        this.post = post;
        this.isLiked = false;
    }

    public int getId() { return id; }
    public User getUser() { return user; }
    public NeighborExperiencePost getPost() { return post; }
}