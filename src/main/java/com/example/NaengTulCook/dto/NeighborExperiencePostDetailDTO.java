package com.example.NaengTulCook.dto;

import com.example.NaengTulCook.entity.NeighborExperiencePost;
import java.util.List;
import java.util.stream.Collectors;

public class NeighborExperiencePostDetailDTO extends NeighborExperiencePostDTO {
    private List<CommentDTO> comments;

    public NeighborExperiencePostDetailDTO(NeighborExperiencePost post, boolean isLiked) {
        super(post, isLiked);
        this.comments = post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getComments() {
        return comments;
    }
}