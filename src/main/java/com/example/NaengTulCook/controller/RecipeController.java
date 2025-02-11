package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

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
}
