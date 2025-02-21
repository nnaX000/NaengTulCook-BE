package com.example.NaengTulCook.dto;

import com.example.NaengTulCook.entity.NeighborExperiencePost;
import java.util.List;
import java.time.format.DateTimeFormatter;

import java.util.stream.Collectors;
import java.util.ArrayList;

public class NeighborExperiencePostDTO {
    private int id;
    private int userId;
    private String nickname;
    private String title;
    private String content;
    private int likeCount;
    private int viewCount;
    private boolean isLiked;
    private int commentCount;
    private String createdAt;

    private List<CommentDTO> comments;
    public NeighborExperiencePostDTO() {
    }

    // ✅ 최신순/인기순 조회용 (댓글 제외)
    public NeighborExperiencePostDTO(NeighborExperiencePost post, boolean isLiked, int commentCount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.nickname = post.getUser().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();
        this.isLiked = isLiked;
        this.commentCount = commentCount;
        this.createdAt = post.getCreatedAt().format(formatter);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getCommentCount() {
        return commentCount;
    }
}