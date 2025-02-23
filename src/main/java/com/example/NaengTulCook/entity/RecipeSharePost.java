package com.example.NaengTulCook.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "recipe_share_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeSharePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 레시피 공유 게시물을 올린 사용자

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String category;
    private String level;
    private String time;
    private String picture;  // 이미지 URL 저장

    @ElementCollection
    @CollectionTable(name = "recipe_share_post_ingredient", joinColumns = @JoinColumn(name = "recipe_share_post_id"))
    @Column(name = "ingredient")
    private List<String> ingredient;

    @ElementCollection
    @CollectionTable(name = "recipe_share_post_seasoning", joinColumns = @JoinColumn(name = "recipe_share_post_id"))
    @Column(name = "seasoning")
    private List<String> seasoning;

    @ElementCollection
    @CollectionTable(name = "recipe_share_post_tool", joinColumns = @JoinColumn(name = "recipe_share_post_id"))
    @Column(name = "tool")
    private List<String> tool;

    @ElementCollection
    @CollectionTable(name = "recipe_share_post_recipe", joinColumns = @JoinColumn(name = "recipe_share_post_id"))
    @Column(name = "recipe")
    private List<String> recipe;

    private int likeCount;
}
