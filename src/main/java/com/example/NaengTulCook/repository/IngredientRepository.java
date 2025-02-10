package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
