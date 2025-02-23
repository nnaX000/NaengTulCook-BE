package com.example.NaengTulCook.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeSharePostRequest {
    private int userId;
    private String title;
    private String content;
    private String category;
    private String level;
    private String time;
    private String picture;
    private List<String> ingredient;
    private List<String> seasoning;
    private List<String> tool;
    private List<String> recipe;
}
