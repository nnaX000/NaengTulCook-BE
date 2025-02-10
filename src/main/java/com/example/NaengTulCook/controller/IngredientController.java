package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    // 이미 존재하는 엑셀 파일을 처리하여 데이터베이스에 저장하는 API
    @Operation(summary = "재료 업로드용 API (프론트측 사용X)", description = "재료 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredients uploaded successfully"),
    })
    @PostMapping("/upload-existing-file")
    public String uploadExistingFile() {
        String filePath = "/Users/gimnayeong/Documents/GitHub/NaengTulCook-BE/ingredients.xlsx";  // 이미 있는 엑셀 파일 경로

        try {
            ingredientService.saveIngredientsFromExcel(filePath);  // 엑셀 파일을 읽고 저장
            return "Ingredients uploaded successfully";
        } catch (IOException e) {
            return "Failed to upload ingredients: " + e.getMessage();
        }
    }
}
