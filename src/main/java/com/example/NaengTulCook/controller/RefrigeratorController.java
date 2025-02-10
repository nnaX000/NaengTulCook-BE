package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.dto.IngredientDTO;
import com.example.NaengTulCook.service.RefrigeratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refrigerator")
public class RefrigeratorController {
    @Autowired
    private RefrigeratorService refrigeratorService;

    @Operation(summary = "사용자가 선택한 재료 저장", description = "사용자가 선택한 재료를 냉장고에 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredients saved to refrigerator successfully!"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/save")
    public String saveSelectedIngredients(@RequestParam int userId, @RequestBody List<String> selectedIngredients) {
        refrigeratorService.saveSelectedIngredients(userId, selectedIngredients);
        return "Ingredients saved to refrigerator successfully!";
    }

    // 사용자가 선택한 재료를 삭제하는 API
    @Operation(summary = "재료 삭제", description = "냉장고에서 재료를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재료 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "재료가 존재하지 않거나 유효하지 않은 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeIngredient(
            @RequestParam int userId,
            @RequestParam String ingredientName) {

        boolean isDeleted = refrigeratorService.removeIngredient(userId, ingredientName);

        if (isDeleted) {
            return new ResponseEntity<>("Ingredient removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Ingredient not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "나의 냉장고 식재료 반환", description = "사용자가 냉장고에 담은 재료들의 이름과 사진을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재료 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientDTO>> getIngredients(
            @RequestParam("userId") int userId) {

        List<IngredientDTO> ingredients = refrigeratorService.getIngredientsByUser(userId);

        if (ingredients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 내용이 없을 경우 204 반환
        }

        return new ResponseEntity<>(ingredients, HttpStatus.OK); // 200 OK와 함께 반환
    }

    @Operation(summary = "나의 냉장고 조미료 반환", description = "사용자가 냉장고에 담은 조미료들의 이름과 사진을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재료 목록이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/condiments")
    public ResponseEntity<List<IngredientDTO>> getCondiments(
            @RequestParam("userId") int userId) {

        List<IngredientDTO> ingredients = refrigeratorService.getCondimentsByUser(userId);

        if (ingredients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 내용이 없을 경우 204 반환
        }

        return new ResponseEntity<>(ingredients, HttpStatus.OK); // 200 OK와 함께 반환
    }
}
