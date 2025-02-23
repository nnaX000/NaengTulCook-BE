package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.entity.Ingredient;
import com.example.NaengTulCook.repository.CookRepository;
import com.example.NaengTulCook.repository.IngredientRepository;
import com.example.NaengTulCook.repository.RecipeRepository;
import com.example.NaengTulCook.service.CookService;
import com.example.NaengTulCook.entity.Cook;
import com.example.NaengTulCook.entity.Recipe;
import com.example.NaengTulCook.service.OpenAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@RestController
@RequestMapping("/api/cook")
public class CookController {

    @Autowired
    private CookService cookService;

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private RestTemplate template;

    @Operation(summary = "사용자가 필터를 적용하여 요리하기 버튼을 눌렀을때 레시피 및 재료를 반환해줌",
            description = "제외할 재료는 쉼표 기준으로 연결해서 보내주세요. 나머지 필터들은 선택한 버튼 텍스트 그대로 보내주시면 됩니다. 재료에 사진 필드가 반환되지 않은 것들은 데이터에 없는 재료이므로 예진님이 제공해주신 디폴트 이미지 쓰시면 될 것 같아요.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recipe created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            description = "레시피 반환 결과",
                                            example = "[{\"seasoning\": [\"소금\", \"후추\", \"우스터 소스\", \"간장\"], \"newPlating\": \"1. 접시에 삶은 소바면을 담고, 볶은 돼지고기와 숙주를 얹어줍니다., 2. 양배추를 곁들여 색과 맛을 더해줍니다. , , 각 항목에는 넘버링되어 있으며, 5문장 이내로 작성되었습니다. 각 항목은 '다'로 끝나는 평서문으로 끝나도록 작성되었습니다.\", \"ingredients\": [{\"name\": \"소바면\", \"picture\": \"\"}, {\"name\": \"우스터 소스\", \"picture\": \"\"}, {\"name\": \"양배추\", \"picture\": \"https://trendithon.s3.ap-southeast-2.amazonaws.com/ingredient/%E1%84%8E%E1%85%A2%E1%84%89%E1%85%A9/%E1%84%8B%E1%85%A3%E1%86%BC%E1%84%87%E1%85%A2%E1%84%8E%E1%85%AE.jpg\"}, {\"name\": \"돼지고기\", \"picture\": \"https://trendithon.s3.ap-southeast-2.amazonaws.com/ingredient/%E1%84%8B%E1%85%B2%E1%86%A8%E1%84%85%E1%85%B2/%E1%84%83%E1%85%AB%E1%84%8C%E1%85%B5%E1%84%80%E1%85%A9%E1%84%80%E1%85%B5.jpg\"}, {\"name\": \"숙주\", \"picture\": \"\"}], \"newCook\": \"1. 소바면을 삶아 식힌 후 찬물에 헹궈 물기를 제거합니다., 2. 돼지고기와 숙주를 같이 볶아 익힌 후 우스터 소스를 넣고 볶습니다.\", \"newSeasoning\": \"1. 우스터 소스의 감칠맛과 소금으로 간을 조절해줍니다.\", \"tool\": [\"프라이팬\", \"젓가락\"]}]"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error in recipe creation"
                    )
            }
    )
    @PostMapping("/gpt_create")
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
            if (recipe == null) {
                return ResponseEntity.status(404).body("레시피를 찾을 수 없습니다.");
            }

            List<String> ingredients = new ArrayList<>();
            List<Map<String, String>> ingredientDetails = new ArrayList<>();

            List<String> essentialIngredients = recipe.getEssentialIngredient();
            List<String> optionalIngredients = recipe.getOptionalIngredient();
            Set<String> excludedSet = new HashSet<>(Arrays.asList(excludedIngredients.split(",")));

            for (String ingredient : essentialIngredients) {
                if (!excludedSet.contains(ingredient.trim())) {
                    ingredients.add(ingredient.trim());
                    Ingredient ingredientEntity = ingredientRepository.findByName(ingredient.trim());
                    Map<String, String> ingredientMap = new HashMap<>();
                    ingredientMap.put("name", ingredientEntity != null ? ingredientEntity.getName() : ingredient.trim());
                    ingredientMap.put("picture", ingredientEntity != null && ingredientEntity.getPicture() != null ? ingredientEntity.getPicture() : "");
                    ingredientDetails.add(ingredientMap);
                }
            }

            for (String ingredient : optionalIngredients) {
                if (!excludedSet.contains(ingredient.trim())) {
                    ingredients.add(ingredient.trim());
                    Ingredient ingredientEntity = ingredientRepository.findByName(ingredient.trim());
                    Map<String, String> ingredientMap = new HashMap<>();
                    ingredientMap.put("name", ingredientEntity != null ? ingredientEntity.getName() : ingredient.trim());
                    ingredientMap.put("picture", ingredientEntity != null && ingredientEntity.getPicture() != null ? ingredientEntity.getPicture() : "");
                    ingredientDetails.add(ingredientMap);
                }
            }

            Cook cookedRecipe = cookService.saveCookedRecipe(userId, excludedIngredients, ingredients, time, difficulty, taste, diet, calorieRange, recipeName);

            Map<String, Object> response = new HashMap<>();
            response.put("ingredients", ingredientDetails);
            response.put("seasoning", recipe.getSeasoning());
            response.put("tool", recipe.getTool());
            response.put("newCook", cookedRecipe.getNewCook());
            response.put("newSeasoning", cookedRecipe.getNewSeasoning());
            response.put("newPlating", cookedRecipe.getNewPlating());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("레시피 생성 중 오류가 발생했습니다.");
        }
    }

    @Operation(summary = "사용자가 필터를 적용하지 않고 요리하기를 눌렀을 때 레시피 반환",
            description = "레시피들을 문장 사이에 \\n을 넣어놨으니 그거 기준으로 쪼개서 줄바꿈 해주시면 될 것 같습니다. 재료에 사진 필드가 반환되지 않은 것들은 데이터에 없는 재료이므로 예진님이 제공해주신 디폴트 이미지 쓰시면 될 것 같아요.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recipe created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            description = "레시피 반환 결과",
                                            example = "{\"seasoning\": [\"소금\", \"후추\", \"우스터 소스\", \"간장\"], " +
                                                    "\"recipeCook\": \"야채(양배추, 당근, 숙주, 파)를 씻고 적당한 크기로 썬다.,\\n    돼지고기는 얇게 썰어 소금과 후추로 밑간을 한다.,\\n    프라이팬을 중불로 달군 후 돼지고기를 넣고 익힌다.,\\n    고기가 익으면 양배추, 당근, 숙주를 넣고 볶는다.,\\n    소바면을 끓는 물에 살짝 데친 후 프라이팬에 넣는다.,\\n    모든 재료를 함께 볶으면서 잘 섞어준다.\", " +
                                                    "\"recipeSeasoning\": \"우스터 소스를 넣고 전체적으로 골고루 섞으며 볶는다.,\\n    간장, 소금, 후추를 추가하여 맛을 조절한다.,\\n    약한 불로 줄이고 1~2분간 간이 배도록 익힌다.\", " +
                                                    "\"recipePlating\": \"완성된 야끼소바를 접시에 담는다.,\\n    파를 송송 썰어 위에 뿌린다.,\\n    기호에 따라 가쓰오부시나 마요네즈를 곁들인다.\", " +
                                                    "\"ingredients\": [{\"name\": \"소바면\"}, {\"name\": \"우스터 소스\"}, {\"name\": \"양배추\", \"picture\": \"https://trendithon.s3.ap-southeast-2.amazonaws.com/ingredient/%E1%84%8E%E1%85%A2%E1%84%89%E1%85%A9/%E1%84%8B%E1%85%A3%E1%86%BC%E1%84%87%E1%85%A2%E1%84%8E%E1%85%AE.jpg\"}, " +
                                                    "{\"name\": \"당근\", \"picture\": \"https://trendithon.s3.ap-southeast-2.amazonaws.com/ingredient/%E1%84%8E%E1%85%A2%E1%84%89%E1%85%A9/%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB.jpg\"}, " +
                                                    "{\"name\": \"돼지고기\", \"picture\": \"https://trendithon.s3.ap-southeast-2.amazonaws.com/ingredient/%E1%84%8B%E1%85%B2%E1%86%A8%E1%84%85%E1%85%B2/%E1%84%83%E1%85%AB%E1%84%8C%E1%85%B5%E1%84%80%E1%85%A9%E1%84%80%E1%85%B5.jpg\"}, " +
                                                    "{\"name\": \"숙주\"}, {\"name\": \"파\"}], " +
                                                    "\"tool\": [[\"프라이팬\", \"젓가락\"]]}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error in recipe creation"
                    )
            }
    )
    @GetMapping("/create")
    public ResponseEntity<?> getRecipeDetails(@RequestParam String recipeName) {
        try {
            Recipe recipe = recipeRepository.findByName(recipeName);
            if (recipe == null) {
                return ResponseEntity.status(404).body("레시피를 찾을 수 없습니다.");
            }

            List<Map<String, String>> ingredientsWithImages = new ArrayList<>();

            // essential_ingredient를 문자열로 변환 후 split
            List<String> ingredients = new ArrayList<>();
            String essentialIngredients = recipe.getEssentialIngredient().toString();
            String optionalIngredients = recipe.getOptionalIngredient().toString();

            if (recipe.getEssentialIngredient() != null && !recipe.getEssentialIngredient().isEmpty()) {
                essentialIngredients = String.join(",", recipe.getEssentialIngredient());
                String[] essentialArr = essentialIngredients.split(",");
                for (String ingredient : essentialArr) {
                    ingredients.add(ingredient.trim());
                }
            }

            if (recipe.getOptionalIngredient() != null && !recipe.getOptionalIngredient().isEmpty()) {
                optionalIngredients = String.join(",", recipe.getOptionalIngredient());
                String[] optionalArr = optionalIngredients.split(",");
                for (String ingredient : optionalArr) {
                    ingredients.add(ingredient.trim());
                }
            }

            // 재료에 대한 이미지 처리
            for (String ingredientName : ingredients) {
                Ingredient ingredient = ingredientRepository.findByName(ingredientName);
                Map<String, String> ingredientData = new HashMap<>();
                if (ingredient != null && ingredient.getPicture() != null) {
                    ingredientData.put("name", ingredient.getName());
                    ingredientData.put("picture", ingredient.getPicture());  // 이미지 URL
                    ingredientsWithImages.add(ingredientData);
                }else{
                    ingredientData.put("name",ingredientName);
                    ingredientsWithImages.add(ingredientData);
                }
            }

            // List<String>을 실제 줄바꿈이 있는 문자열로 변환
            String recipeCook = joinWithNewLine(recipe.getRecipeCook());
            String recipeSeasoning = joinWithNewLine(recipe.getRecipeSeasoning());
            String recipePlating = joinWithNewLine(recipe.getRecipePlating());

            // 응답 구성
            Map<String, Object> response = new HashMap<>();
            response.put("ingredients", ingredientsWithImages); // 이미지가 포함된 재료 목록
            response.put("tool", List.of(recipe.getTool()));
            response.put("recipeCook", recipeCook.trim());
            response.put("recipeSeasoning", recipeSeasoning.trim());
            response.put("seasoning", recipe.getSeasoning());
            response.put("recipePlating", recipePlating.trim());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버에서 오류가 발생했습니다.");
        }
    }

    private String joinWithNewLine(List<String> list) {
        return String.join("\n", list);
    }

    @Operation(
            summary = "유저 필터 조회",
            description = "userId를 이용해 해당 유저의 최근 필터 설정을 조회합니다. 만약 특정 유저에 대한 필터 데이터가 없다면 200ok는 뜨지만 body에 null을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 필터 정보를 반환",
                            content = @Content(schema = @Schema(
                                    example = "{\n" +
                                            "    \"filter3Level\": \"중급\",\n" +
                                            "    \"filter2Time\": \"10분 이하\",\n" +
                                            "    \"filter5Diet\": \"케토\",\n" +
                                            "    \"filter6Calorie\": \"저칼로리\",\n" +
                                            "    \"filter4Flavor\": \"짭짤한,달달한\",\n" +
                                            "    \"filter1Except\": \"당근,양파\"\n" +
                                            "}"
                            ))
                    ),
            }
    )
    @GetMapping("/user-filter")
    ResponseEntity<?> getUserFilters(@RequestParam int userId) {
        Optional<Cook> cookOptional = cookRepository.findTopByUserIdOrderByIdDesc(userId);

        if (cookOptional.isEmpty()) {
            return ResponseEntity.ok(null);
        }

        Cook cook = cookOptional.get();
        
        Map<String, String> filters = Map.of(
                "filter1Except", cook.getFilter1Except(),
                "filter2Time", cook.getFilter2Time(),
                "filter3Level", cook.getFilter3Level(),
                "filter4Flavor", cook.getFilter4Flavor(),
                "filter5Diet", cook.getFilter5Diet(),
                "filter6Calorie", cook.getFilter6Calorie()
        );

        return ResponseEntity.ok(filters);
    }

}


