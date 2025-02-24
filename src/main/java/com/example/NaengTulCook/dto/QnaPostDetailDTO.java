package com.example.NaengTulCook.dto;

import com.example.NaengTulCook.entity.QnaPost;
import java.util.List;
import java.util.stream.Collectors;

public class QnaPostDetailDTO extends QnaPostDTO {
    private List<QnaCommentDTO> comments;
    private int commentCount;

    public QnaPostDetailDTO(QnaPost post, boolean isLiked, int commentCount) {
        super(post, isLiked, commentCount);
        this.comments = post.getComments().stream()
                .map(QnaCommentDTO::new)
                .collect(Collectors.toList());
        this.commentCount = post.getComments().size();
    }

    public List<QnaCommentDTO> getComments() {
        return comments;
    }

    public int getCommentCount() {
        return commentCount;
    }
}