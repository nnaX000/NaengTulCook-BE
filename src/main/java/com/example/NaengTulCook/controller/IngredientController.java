package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.entity.Ingredient;
import com.example.NaengTulCook.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    // 전체 재료 반환 (가나다 순)
    @Operation(summary = "전체 재료 반환", description = "가나다 순으로 정렬된 전체 재료를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재료 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/all")
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredientsSorted();
    }

    // 냉동식품만 반환 (가나다 순)
    @Operation(summary = "냉동식품 반환", description = "카테고리가 냉동식품인 재료를 가나다 순으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "냉동식품 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/frozen")
    public List<Ingredient> getFrozenIngredients() {
        return ingredientService.getIngredientsByCategorySorted("냉동식품");
    }

    // 곡물만 반환 (가나다 순)
    @Operation(summary = "곡물 반환", description = "카테고리가 곡물인 재료를 가나다 순으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "곡물 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/grains")
    public List<Ingredient> getGrains() {
        return ingredientService.getIngredientsByCategorySorted("곡물");
    }

    // 과일만 반환 (가나다 순)
    @Operation(summary = "과일 반환", description = "카테고리가 과일인 재료를 가나다 순으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "과일 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/fruits")
    public List<Ingredient> getFruits() {
        return ingredientService.getIngredientsByCategorySorted("과일");
    }

    // 유제품만 반환 (가나다 순)
    @Operation(summary = "유제품 반환", description = "카테고리가 유제품인 재료를 가나다 순으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유제품 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/dairy")
    public List<Ingredient> getDairy() {
        return ingredientService.getIngredientsByCategorySorted("유제품");
    }

    // 육류만 반환 (가나다 순)
    @Operation(summary = "육류 반환", description = "카테고리가 육류인 재료를 가나다 순으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "육류 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/meat")
    public List<Ingredient> getMeat() {
        return ingredientService.getIngredientsByCategorySorted("육류");
    }

    // 조미료만 반환 (가나다 순)
    @Operation(summary = "조미료 반환", description = "카테고리가 조미료인 재료를 가나다 순으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조미료 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/seasonings")
    public List<Ingredient> getSeasonings() {
        return ingredientService.getIngredientsByCategorySorted("조미료");
    }

    // 채소만 반환 (가나다 순)
    @Operation(summary = "채소 반환", description = "카테고리가 채소인 재료를 가나다 순으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채소 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/vegetables")
    public List<Ingredient> getVegetables() {
        return ingredientService.getIngredientsByCategorySorted("채소");
    }

    // 해산물만 반환 (가나다 순)
    @Operation(summary = "해산물 반환", description = "카테고리가 해산물인 재료를 가나다 순으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해산물 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/seafood")
    public List<Ingredient> getSeafood() {
        return ingredientService.getIngredientsByCategorySorted("해산물");
    }

    // 이름으로 검색 (가나다 순)
    @Operation(summary = "이름으로 재료 검색", description = "이름에 맞는 재료를 가나다 순으로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재료 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/searchByName")
    public List<Ingredient> searchByName(@RequestParam String name) {
        return ingredientService.getIngredientsByNameSorted(name);
    }
}
