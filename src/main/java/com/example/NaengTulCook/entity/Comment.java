package com.example.NaengTulCook.entity;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private NeighborExperiencePost post;

    private String content;

    public Comment() {}

    public Comment(User user, NeighborExperiencePost post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public Integer getId() { return id; }
    public User getUser() { return user; }
    public NeighborExperiencePost getPost() { return post; }
    public String getContent() { return content; }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + (user != null ? user.getNickname() : "null") +
                ", postId=" + (post != null ? post.getId() : "null") +
                ", content='" + content + '\'' +
                '}';
    }
}