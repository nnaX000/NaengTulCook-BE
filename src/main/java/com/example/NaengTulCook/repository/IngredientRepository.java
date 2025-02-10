package com.example.NaengTulCook.repository;

import com.example.NaengTulCook.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    List<Ingredient> findAllByOrderByNameAsc();

    List<Ingredient> findByCategoryIgnoreCaseOrderByNameAsc(String category);

    List<Ingredient> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

    Ingredient findByName(String name);
}
