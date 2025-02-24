package com.example.NaengTulCook.service;

import com.example.NaengTulCook.dto.QnaCommentDTO;
import com.example.NaengTulCook.dto.QnaPostDTO;
import com.example.NaengTulCook.dto.QnaPostDetailDTO;
import com.example.NaengTulCook.entity.QnaComment;
import com.example.NaengTulCook.entity.QnaPost;
import com.example.NaengTulCook.entity.QnaLike;
import com.example.NaengTulCook.entity.User;
import com.example.NaengTulCook.repository.QnaCommentRepository;
import com.example.NaengTulCook.repository.QnaPostLikeRepository;
import com.example.NaengTulCook.repository.QnaPostRepository;
import com.example.NaengTulCook.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QnaPostService {

    private final QnaPostRepository qnaPostRepository;
    private final QnaPostLikeRepository qnaPostLikeRepository;
    private final QnaCommentRepository qnaCommentRepository;
    private final UserRepository userRepository;

    public QnaPostService(QnaPostRepository qnaPostRepository,
                          QnaPostLikeRepository qnaPostLikeRepository,
                          QnaCommentRepository qnaCommentRepository,
                          UserRepository userRepository) {
        this.qnaPostRepository = qnaPostRepository;
        this.qnaPostLikeRepository = qnaPostLikeRepository;
        this.qnaCommentRepository = qnaCommentRepository;
        this.userRepository = userRepository;
    }

    /**
     * 📌 QnA 게시글 등록
     */
    public QnaPostDTO createPost(QnaPostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        QnaPost post = new QnaPost();
        post.setUser(user);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setCommentCount(0);

        QnaPost savedPost = qnaPostRepository.save(post);
        int commentCount = qnaCommentRepository.countByQnaPostId(savedPost.getId());
        return new QnaPostDTO(savedPost, false, commentCount);
    }

    /**
     * 📌 최신순 QnA 게시글 조회
     */
    public List<QnaPostDTO> getPostsSortedByLatest(int userId) {
        return qnaPostRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(post -> {
                    boolean isLiked = qnaPostLikeRepository.existsByUserIdAndQnaPostId(userId, post.getId());
                    int commentCount = qnaCommentRepository.countByQnaPostId(post.getId());
                    return new QnaPostDTO(post, isLiked, commentCount);
                })
                .collect(Collectors.toList());
    }

    /**
     * 📌 인기순 QnA 게시글 조회 (좋아요 기준)
     */
    public List<QnaPostDTO> getPostsSortedByLikes(int userId) {
        return qnaPostRepository.findAllByOrderByLikeCountDesc().stream()
                .map(post -> {
                    boolean isLiked = qnaPostLikeRepository.existsByUserIdAndQnaPostId(userId, post.getId());
                    int commentCount = qnaCommentRepository.countByQnaPostId(post.getId());
                    return new QnaPostDTO(post, isLiked, commentCount);
                })
                .collect(Collectors.toList());
    }

    /**
     * 📌 특정 QnA 게시글 조회 (댓글 포함)
     */
    public QnaPostDetailDTO getPostDetail(int postId, int userId) {
        QnaPost post = qnaPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        boolean isLiked = qnaPostLikeRepository.existsByUserIdAndQnaPostId(userId, postId);
        int commentCount = qnaCommentRepository.countByQnaPostId(postId);

        return new QnaPostDetailDTO(post, isLiked, commentCount);
    }

    /**
     * 📌 댓글 등록
     */
    @Transactional
    public QnaCommentDTO addComment(int postId, int userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        QnaPost post = qnaPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        QnaComment comment = new QnaComment(user, post, content);
        qnaCommentRepository.save(comment);
        return new QnaCommentDTO(comment);
    }

    /**
     * 📌 게시글 좋아요/취소 (스크랩 기능 포함)
     */
    @Transactional
    public boolean toggleLike(int postId, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        QnaPost post = qnaPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Optional<QnaLike> existingLike = qnaPostLikeRepository.findByUserAndQnaPost(user, post);
        if (existingLike.isPresent()) {
            qnaPostLikeRepository.delete(existingLike.get());
            post.decreaseLikeCount();
            qnaPostRepository.save(post);
            return false;
        } else {
            qnaPostLikeRepository.save(new QnaLike(user, post, true));
            post.increaseLikeCount();
            qnaPostRepository.save(post);
            return true;
        }
    }
}