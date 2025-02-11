package com.example.NaengTulCook.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String forwardName;
    private String name;

    @ElementCollection
    private List<String> essentialIngredient;

    @ElementCollection
    private List<String> optionalIngredient;

    @ElementCollection
    private List<String> seasoning;

    @ElementCollection
    private List<String> tool;

    @ElementCollection
    private List<String> recipeCook;

    @ElementCollection
    private List<String> recipeSeasoning;

    @ElementCollection
    private List<String> recipePlating;

    private int level;
    private int time;
    private String catchphrase;
    private String picture;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForwardName() {
        return forwardName;
    }

    public void setForwardName(String forwardName) {
        this.forwardName = forwardName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEssentialIngredient() {
        return essentialIngredient;
    }

    public void setEssentialIngredient(List<String> essentialIngredient) {
        this.essentialIngredient = essentialIngredient;
    }

    public List<String> getOptionalIngredient() {
        return optionalIngredient;
    }

    public void setOptionalIngredient(List<String> optionalIngredient) {
        this.optionalIngredient = optionalIngredient;
    }

    public List<String> getSeasoning() {
        return seasoning;
    }

    public void setSeasoning(List<String> seasoning) {
        this.seasoning = seasoning;
    }

    public List<String> getRecipeCook() {
        return recipeCook;
    }

    public void setRecipeCook(List<String> recipeCook) {
        this.recipeCook = recipeCook;
    }

    public List<String> getRecipeSeasoning() {
        return recipeSeasoning;
    }

    public void setRecipeSeasoning(List<String> recipeSeasoning) {
        this.recipeSeasoning = recipeSeasoning;
    }

    public List<String> getRecipePlating() {
        return recipePlating;
    }

    public void setRecipePlating(List<String> recipePlating) {
        this.recipePlating = recipePlating;
    }

    public List<String> getTool() {
        return tool;
    }

    public void setTool(List<String> tool) {
        this.tool = tool;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCatchphrase() {
        return catchphrase;
    }

    public void setCatchphrase(String catchphrase) {
        this.catchphrase = catchphrase;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
