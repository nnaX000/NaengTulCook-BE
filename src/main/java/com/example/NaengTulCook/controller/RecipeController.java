package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.entity.User;
import com.example.NaengTulCook.repository.UserRepository;
import com.example.NaengTulCook.service.RecipeService;
import com.example.NaengTulCook.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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


    @Operation(
            summary = "레시피 랜덤 추천",
            description = "새로고침시에도 이 api로 요청 보내시면 다른 레시피 추천 가능합니다. level 경우 0-초급/1-중급/2-고급 입니다. forward_name는 요리이름 위에 있는 조그만 글씨고 ingredients는 요리하는 데 필요한 전체 재료입니다. optional_absence는 꼭 필요하진 않지만 있으면 좋은 재료 중 내 냉장고에 없는 재료이고 essential_absence는 꼭 필요한 재료지만 내 냉장고에 없는 재료입니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "추천된 레시피 목록",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            description = "레시피 추천 결과",
                                            example = "[{\"level\": 1, \"catchphrase\": \"매콤한 감칠맛이 일품인 요리예요.\", \"forward_name\": \"짭조름한 양념이 고등어 속까지 배어 밥도둑이 되는 조림 요리\", \"name\": \"고등어조림\", \"ingredients\": [\"고등어\", \"무\", \"양파\", \"대파\", \"청양고추\", \"홍고추\", \"애호박\", \"감자\"], \"optional_absence\": [\"청양고추\", \"홍고추\", \"애호박\"], \"time\": 40, \"picture\": \"https://trendithon.s3.ap-southeast-2.amazonaws.com/cook_2/korea/%E1%84%80%E1%85%A9%E1%84%83%E1%85%B3%E1%86%BC%E1%84%8B%E1%85%A5%E1%84%8C%E1%85%A9%E1%84%85%E1%85%B5%E1%86%B7.jpg\", \"essential_absence\": [\"무\", \"대파\"]}]"
                                    )
                            )
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes uploaded successfully")
    })
    @GetMapping("/recommend")
    public List<Map<String, Object>> getRecommendedRecipes(@RequestParam int userId) {
        User user = userRepository.findUsersById(userId);
        return recipeService.recommendRecipes(user);
    }
}
