package com.example.NaengTulCook.dto;

import com.example.NaengTulCook.entity.Comment;

/**
 * 📌 댓글 DTO (Data Transfer Object)
 * 댓글 데이터를 API 응답으로 변환하는 클래스
 */
public class CommentDTO {
    private int id;
    private int userId;
    private String username;
    private String content;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.username = comment.getUser().getNickname();  // 닉네임 추가
        this.content = comment.getContent();
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; } // 닉네임 반환
    public String getContent() { return content; }
}