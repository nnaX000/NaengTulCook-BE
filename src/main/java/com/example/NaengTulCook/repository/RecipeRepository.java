package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.Recipe;
import com.example.NaengTulCook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, String> {
    List<Recipe> findAll();

    Recipe findByName(String recipeName);
}

