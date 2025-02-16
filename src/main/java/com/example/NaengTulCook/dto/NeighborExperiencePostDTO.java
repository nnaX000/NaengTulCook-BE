package com.example.NaengTulCook.dto;

public class NeighborExperiencePostDTO {
    private int userId;
    private String title;
    private String content;

    // 기본 생성자
    public NeighborExperiencePostDTO() {}

    // 생성자
    public NeighborExperiencePostDTO(int userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    // Getter & Setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}