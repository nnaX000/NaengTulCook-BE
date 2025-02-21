package com.example.NaengTulCook.dto;

import com.example.NaengTulCook.entity.NeighborExperiencePost;
import java.util.List;
import java.util.stream.Collectors;

public class NeighborExperiencePostDetailDTO extends NeighborExperiencePostDTO {
    private List<CommentDTO> comments;
    private int commentCount;

    public NeighborExperiencePostDetailDTO(NeighborExperiencePost post, boolean isLiked, int commentCount) {
        super(post, isLiked, commentCount);
        this.comments = post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
        this.commentCount = post.getComments().size();
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public int getCommentCount() {
        return commentCount;
    }
}