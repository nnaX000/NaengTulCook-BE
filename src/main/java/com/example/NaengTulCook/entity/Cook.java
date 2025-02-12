package com.example.NaengTulCook.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cook")
public class Cook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @CollectionTable(name = "filter1_except", joinColumns = @JoinColumn(name = "cook_id"))
    @Column(name = "excluded_ingredient")
    private String filter1Except;

    private String filter2Time;
    private String filter3Level;
    private String filter4Flavor;
    private String filter5Diet;
    private String filter6Calorie;

    @CollectionTable(name = "new_cook", joinColumns = @JoinColumn(name = "cook_id"))
    @Column(name = "new_cooking_method")
    private String newCook;

    @CollectionTable(name = "new_seasoning", joinColumns = @JoinColumn(name = "cook_id"))
    @Column(name = "new_seasoning_method")
    private String newSeasoning;

    @CollectionTable(name = "new_plating", joinColumns = @JoinColumn(name = "cook_id"))
    @Column(name = "new_plating_method")
    private String newPlating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getFilter1Except() {
        return filter1Except;
    }

    public void setFilter1Except(String filter1Except) {
        this.filter1Except = filter1Except;
    }

    public String getFilter2Time() {
        return filter2Time;
    }

    public void setFilter2Time(String filter2Time) {
        this.filter2Time = filter2Time;
    }

    public String getFilter3Level() {
        return filter3Level;
    }

    public void setFilter3Level(String filter3Level) {
        this.filter3Level = filter3Level;
    }

    public String getFilter4Flavor() {
        return filter4Flavor;
    }

    public void setFilter4Flavor(String filter4Flavor) {
        this.filter4Flavor = filter4Flavor;
    }

    public String getFilter5Diet() {
        return filter5Diet;
    }

    public void setFilter5Diet(String filter5Diet) {
        this.filter5Diet = filter5Diet;
    }

    public String getFilter6Calorie() {
        return filter6Calorie;
    }

    public void setFilter6Calorie(String filter6Calorie) {
        this.filter6Calorie = filter6Calorie;
    }

    public String getNewCook() {
        return newCook;
    }

    public void setNewCook(String newCook) {
        this.newCook = newCook;
    }

    public String getNewSeasoning() {
        return newSeasoning;
    }

    public void setNewSeasoning(String newSeasoning) {
        this.newSeasoning = newSeasoning;
    }

    public String getNewPlating() {
        return newPlating;
    }

    public void setNewPlating(String newPlating) {
        this.newPlating = newPlating;
    }
}
