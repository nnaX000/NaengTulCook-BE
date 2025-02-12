package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.repository.CookRepository;
import com.example.NaengTulCook.repository.RecipeRepository;
import com.example.NaengTulCook.service.CookService;
import com.example.NaengTulCook.entity.Cook;
import com.example.NaengTulCook.entity.Recipe;
import com.example.NaengTulCook.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@RestController
@RequestMapping("/api/cooked-recipes")
public class CookController {

    @Autowired
    private CookService cookService;

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private RestTemplate template;

    @PostMapping("/create")
    public ResponseEntity<?> createCookedRecipe(
            @RequestParam int userId,
            @RequestParam String excludedIngredients,
            @RequestParam String time,
            @RequestParam String difficulty,
            @RequestParam String taste,
            @RequestParam String diet,
            @RequestParam String calorieRange,
            @RequestParam String recipeName) {

        try {

            Recipe recipe = recipeRepository.findByName(recipeName);

            List<String> ingredients = new ArrayList<>();
            List<String> essentialIngredients = recipe.getEssentialIngredient();
            List<String> optionalIngredients = recipe.getOptionalIngredient();

            Set<String> excludedSet = new HashSet<>(Arrays.asList(excludedIngredients.split(",")));
            for (String ingredient : essentialIngredients) {
                if (!excludedSet.contains(ingredient.trim())) {
                    ingredients.add(ingredient.trim());
                }
            }
            for (String ingredient : optionalIngredients) {
                if (!excludedSet.contains(ingredient.trim())) {
                    ingredients.add(ingredient.trim());
                }
            }

            Cook cookedRecipe = cookService.saveCookedRecipe(userId, excludedIngredients, ingredients, time, difficulty, taste, diet, calorieRange, recipeName);

            Map<String, Object> response = new HashMap<>();
            response.put("ingredients", ingredients);
            response.put("seasoning", recipe.getSeasoning());
            response.put("tool", recipe.getTool());
            response.put("newCook", cookedRecipe.getNewCook());
            response.put("newSeasoning", cookedRecipe.getNewSeasoning());
            response.put("newPlating", cookedRecipe.getNewPlating());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("레시피 생성 중 오류가 발생했습니다.");
        }
    }

}

