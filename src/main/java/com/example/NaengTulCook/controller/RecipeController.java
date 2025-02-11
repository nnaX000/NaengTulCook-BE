package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.entity.User;
import com.example.NaengTulCook.repository.UserRepository;
import com.example.NaengTulCook.service.RecipeService;
import com.example.NaengTulCook.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "레시피 데이터 업로드(프론트 사용x)", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipes uploaded successfully")
    })
    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile() throws IOException {
        String filePath = "/Users/gimnayeong/Documents/GitHub/NaengTulCook-BE/recipe_completed_x.xlsx"; 

        recipeService.saveRecipesFromExcel(filePath);

        return new ResponseEntity<>("Recipes uploaded successfully", HttpStatus.CREATED);
    }

    @GetMapping("/recommend")
    public List<Map<String, Object>> getRecommendedRecipes(@RequestParam int userId) {
        User user = userRepository.findUsersById(userId);
        return recipeService.recommendRecipes(user);
    }
}
