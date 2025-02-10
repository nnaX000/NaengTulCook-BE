package com.example.NaengTulCook.entity;

import jakarta.persistence.*;

@Entity
public class Ingredient {

    @Id
    private int ingredientId;

    private String category;
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String picture;

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
