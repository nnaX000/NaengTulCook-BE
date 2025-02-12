package com.example.NaengTulCook.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userIdentifier;
    private String nickname;
    private String password;
    private int age;
    private String family;

    private int skill;

    @ElementCollection
    private List<String> favorite;

    @ElementCollection
    private List<String> ingredient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public List<String> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<String> favorite) {
        this.favorite = favorite;
    }

    public List<String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<String> ingredient) {
        this.ingredient = ingredient;
    }
}
