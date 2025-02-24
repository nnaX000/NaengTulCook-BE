package com.example.NaengTulCook.dto;

import com.example.NaengTulCook.entity.QnaComment;

/**
 * 📌 QnA 댓글 DTO
 */
public class QnaCommentDTO {
    private int id;
    private int userId;
    private String nickname;
    private String content;

    public QnaCommentDTO(QnaComment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.content = comment.getContent().replaceAll("^\"|\"$", ""); // 앞뒤 따옴표 제거
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public String getContent() { return content; }
}