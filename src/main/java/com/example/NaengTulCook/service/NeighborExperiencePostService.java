//package com.example.NaengTulCook.service;
//
//import com.example.NaengTulCook.dto.NeighborExperiencePostDTO;
//import com.example.NaengTulCook.entity.NeighborExperiencePost;
//import com.example.NaengTulCook.repository.NeighborExperiencePostRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class NeighborExperiencePostService {
//
//    private final NeighborExperiencePostRepository postRepository;
//
//    public NeighborExperiencePostService(NeighborExperiencePostRepository postRepository) {
//        this.postRepository = postRepository;
//    }
//
//    /**
//     * 📌 게시글 생성 API
//     */
//    @Transactional
//    public NeighborExperiencePost createPost(NeighborExperiencePostDTO dto) {
//        // 🔹 새로운 게시글 생성
//        NeighborExperiencePost post = new NeighborExperiencePost(
//                dto.getUserId(), dto.getTitle(), dto.getContent()
//        );
//
//        // 🔹 게시글 저장
//        return postRepository.save(post);
//    }
//}

package com.example.NaengTulCook.service;

import com.example.NaengTulCook.dto.CommentDTO;
import com.example.NaengTulCook.dto.NeighborExperiencePostDTO;
import com.example.NaengTulCook.entity.Comment;
import com.example.NaengTulCook.entity.NeighborExperiencePost;
import com.example.NaengTulCook.entity.PostLike;
import com.example.NaengTulCook.entity.User;
import com.example.NaengTulCook.repository.CommentRepository;
import com.example.NaengTulCook.repository.NeighborExperiencePostRepository;
import com.example.NaengTulCook.repository.PostLikeRepository;
import com.example.NaengTulCook.repository.UserRepository;
import com.example.NaengTulCook.dto.NeighborExperiencePostDetailDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NeighborExperiencePostService {

    private final NeighborExperiencePostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public NeighborExperiencePostService(NeighborExperiencePostRepository postRepository,
                                         PostLikeRepository postLikeRepository,
                                         CommentRepository commentRepository,
                                         UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }
    public NeighborExperiencePostDTO createPost(NeighborExperiencePostDTO postDTO) {
        // 유저 ID를 이용해 유저 조회
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        // 새로운 게시글 생성
        NeighborExperiencePost post = new NeighborExperiencePost();
        post.setUser(user);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setLikeCount(0);
        post.setViewCount(0);

        // 저장 후 DTO로 변환하여 반환
        NeighborExperiencePost savedPost = postRepository.save(post);
        return new NeighborExperiencePostDTO(savedPost, false);
    }
//    /**
//     * 최신순 게시글 조회 (createdAt 기준 내림차순)
//     */
//    public List<NeighborExperiencePostDTO> getPostsSortedByLatest() {
//        return postRepository.findAllByOrderByCreatedAtDesc().stream()
//                .map(NeighborExperiencePostDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * 인기순 게시글 조회 (likeCount 기준 내림차순)
//     */
//    public List<NeighborExperiencePostDTO> getPostsSortedByLikes() {
//        return postRepository.findAllByOrderByLikeCountDesc().stream()
//                .map(NeighborExperiencePostDTO::new)
//                .collect(Collectors.toList());
//    }

    /**
     * 최신순 게시글 조회 (createdAt 기준 내림차순) + isLiked 포함 (댓글 제외)
     */
    public List<NeighborExperiencePostDTO> getPostsSortedByLatest(int userId) {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(post -> {
                    boolean isLiked = postLikeRepository.existsByUserIdAndPostId(userId, post.getId());
                    return new NeighborExperiencePostDTO(post, isLiked);
                })
                .collect(Collectors.toList());
    }

    /**
     * 인기순 게시글 조회 (likeCount 기준 내림차순) + isLiked 포함 (댓글 제외)
     */
    public List<NeighborExperiencePostDTO> getPostsSortedByLikes(int userId) {
        return postRepository.findAllByOrderByLikeCountDesc().stream()
                .map(post -> {
                    boolean isLiked = postLikeRepository.existsByUserIdAndPostId(userId, post.getId());
                    return new NeighborExperiencePostDTO(post, isLiked);
                })
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글 조회 (댓글 포함)
     */
    public NeighborExperiencePostDetailDTO getPostDetail(int postId, int userId) {
        NeighborExperiencePost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        boolean isLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        return new NeighborExperiencePostDetailDTO(post, isLiked);
    }

    /**
     * 댓글 등록
     */
    @Transactional
    public CommentDTO addComment(int postId, int userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        NeighborExperiencePost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Comment comment = new Comment(user, post, content);
        commentRepository.save(comment);
        return new CommentDTO(comment);
    }

    /**
     * 게시글 좋아요/취소
     */
    @Transactional
    public boolean toggleLike(int postId, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        NeighborExperiencePost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Optional<PostLike> existingLike = postLikeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get()); // 좋아요 취소
            post.decreaseLikeCount();
            postRepository.save(post);
            return false;
        } else {
            postLikeRepository.save(new PostLike(user, post, true));
            post.increaseLikeCount();
            postRepository.save(post);
            return true;
        }
    }
}