package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.entity.Ingredient;
import com.example.NaengTulCook.entity.RecipeLike;
import com.example.NaengTulCook.entity.RecipeSharePost;
import com.example.NaengTulCook.entity.User;
import com.example.NaengTulCook.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipe-share")
public class RecipeShareController {

    private final RecipeSharePostRepository recipeSharePostRepository;
    private final UserRepository userRepository;
    private static final String UPLOAD_DIR = "/uploads/";
    private final RecipeLikeRepository recipeLikeRepository;
    private final IngredientRepository ingredientRepository;


    public RecipeShareController(RecipeSharePostRepository recipeSharePostRepository, UserRepository userRepository,RecipeLikeRepository recipeLikeRepository,IngredientRepository ingredientRepository) {
        this.recipeSharePostRepository = recipeSharePostRepository;
        this.userRepository = userRepository;
        this.recipeLikeRepository = recipeLikeRepository;
        this.ingredientRepository = ingredientRepository;
    }


    @Operation(
            summary = "레시피 공유 게시글 생성",
            description = "사용자가 레시피를 공유하는 게시글을 생성합니다. 파일(이미지)을 업로드할 수 있으며, JSON 배열 형태의 재료, 조미료, 도구, 레시피 리스트를 포함합니다. 등록 성공했을때 반환되는 id값을 저장시켜서 레시피 확인 버튼에 대한 API 쏘실때 사용하면 될 것 같아요 "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공",
                    content = @Content(schema = @Schema(example = "{\"id\": 4}"))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (유저 ID 없음)"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류 (파일 업로드 실패)")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRecipeSharePost(
            @RequestPart("userId") int userId,
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart("category") String category,
            @RequestPart("level") String level,
            @RequestPart("time") String time,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("ingredient") String ingredientJson,
            @RequestPart("seasoning") String seasoningJson,
            @RequestPart("tool") String toolJson,
            @RequestPart("recipe") String recipeJson) {

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 user_id가 존재하지 않습니다."));

            ObjectMapper objectMapper = new ObjectMapper();
            List<String> ingredient = objectMapper.readValue(ingredientJson, new TypeReference<List<String>>() {});
            List<String> seasoning = objectMapper.readValue(seasoningJson, new TypeReference<List<String>>() {});
            List<String> tool = objectMapper.readValue(toolJson, new TypeReference<List<String>>() {});
            List<String> recipe = objectMapper.readValue(recipeJson, new TypeReference<List<String>>() {});


            String pictureUrl = null;
            if (file != null && !file.isEmpty()) {

                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                Path uploadPath = Paths.get(uploadDir);


                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }


                String fileExtension = "";
                if (file.getOriginalFilename() != null && file.getOriginalFilename().contains(".")) {
                    fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                }
                String fileName = UUID.randomUUID().toString() + fileExtension;
                Path filePath = uploadPath.resolve(fileName);


                file.transferTo(filePath.toFile());


                pictureUrl = "/uploads/" + fileName;
            }


            RecipeSharePost newPost = RecipeSharePost.builder()
                    .user(user)
                    .title(title)
                    .content(content)
                    .category(category)
                    .level(level)
                    .time(time)
                    .picture(pictureUrl)
                    .ingredient(ingredient)
                    .seasoning(seasoning)
                    .tool(tool)
                    .recipe(recipe)
                    .likeCount(0)
                    .build();

            RecipeSharePost savedPost = recipeSharePostRepository.save(newPost);

            return ResponseEntity.created(URI.create("/api/recipe-share/" + savedPost.getId()))
                    .body(Collections.singletonMap("id", savedPost.getId()));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{postId}")
    @Operation(
            summary = "게시글 상세 조회(레시피 확인 버튼 눌렀을때랑 슬라이드에서 레시피 클릭했을때 사용하시면 될 것 같아요)",
            description = "특정 게시글 ID를 받아 게시글 정보를 조회합니다. 작성자의 닉네임도 포함됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공",
                    content = @Content(schema = @Schema(example = """
                        {
                            "id": 4,
                            "userNickname": "나영이",
                            "title": "김치볶음밥",
                            "userAge": 20,
                            "content": "김치와 밥을 볶아 만든 요리",
                            "category": "한식",
                            "level": "초급",
                            "userFamily": "1인가구",
                            "time": "10분",
                            "picture": "/uploads/2563e9ba-2d3e-4f6e-af07-3a79899790fe.jpeg",
                            "ingredient": [
                                "김치 200g",
                                "밥 2공기",
                                "햄 7g"
                            ],
                            "seasoning": [
                                "간장 20g",
                                "고추장 10g"
                            ],
                            "tool": [
                                "프라이팬",
                                "주걱"
                            ],
                            "recipe": [
                                "김치를 볶는다",
                                "햄을 추가한다",
                                "밥을 넣고 볶는다"
                            ]
                        }
                    """))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음",
                    content = @Content(schema = @Schema(example = "{ \"message\": \"해당 게시글을 찾을 수 없습니다.\" }")))
    })
    public ResponseEntity<?> getRecipeSharePost(@PathVariable("postId") int postId) {

        Optional<RecipeSharePost> optionalPost = recipeSharePostRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "해당 게시글을 찾을 수 없습니다."));
        }

        RecipeSharePost post = optionalPost.get();

        Map<String, Object> response = new HashMap<>();
        response.put("id", post.getId());
        response.put("userNickname", post.getUser().getNickname());
        response.put("userAge", post.getUser().getAge());
        response.put("userFamily", post.getUser().getFamily());
        response.put("title", post.getTitle());
        response.put("content", post.getContent());
        response.put("category", post.getCategory());
        response.put("level", post.getLevel());
        response.put("time", post.getTime());
        response.put("picture", post.getPicture());
        response.put("ingredient", post.getIngredient());
        response.put("seasoning", post.getSeasoning());
        response.put("tool", post.getTool());
        response.put("recipe", post.getRecipe());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @Operation(
            summary = "모든 레시피 조회(자유 레시피 무한스크롤 부분)",
            description = "모든 레시피 목록을 불러옵니다. 제목, 작성자 닉네임, 좋아요 개수, 사진만 포함됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "레시피 목록 조회 성공",
                    content = @Content(schema = @Schema(example = """
                        [
                            {
                                "id": 4,
                                "title": "김치볶음밥",
                                "userNickname": "나영이",
                                "likeCount": 15,
                                "picture": "/uploads/706aa038-1479-4f6e-af07-3a79899790fe.jpeg"
                            },
                            {
                                "id": 5,
                                "title": "된장찌개",
                                "userNickname": "철수",
                                "likeCount": 8,
                                "picture": "/uploads/a893fcb2-25c3-467e-9345-df9f98094c5d.jpeg"
                            }
                        ]
                    """))),
            @ApiResponse(responseCode = "204", description = "데이터 없음")
    })
    public ResponseEntity<?> getAllRecipeSharePosts() {

        List<RecipeSharePost> posts = recipeSharePostRepository.findAll();

        if (posts.isEmpty()) {
            return ResponseEntity.status(204).build(); // No Content
        }


        List<Map<String, Object>> response = posts.stream()
                .map(post -> {
                    Map<String, Object> postMap = new HashMap<>();
                    postMap.put("id", post.getId());
                    postMap.put("title", post.getTitle());
                    postMap.put("userNickname", post.getUser().getNickname());
                    postMap.put("likeCount", post.getLikeCount());
                    postMap.put("picture", post.getPicture());
                    return postMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/scrap")
    @Operation(
            summary = "레시피게시글 스크랩",
            description = "특정 레시피게시글을 스크랩"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 추가 성공",
                    content = @Content(schema = @Schema(example = """
                        {
                            "postId": 4,
                            "message": "스크랩이 추가되었습니다."
                        }
                    """))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (게시글 또는 유저 없음)",
                    content = @Content(schema = @Schema(example = "{ \"message\": \"해당 게시글 또는 유저를 찾을 수 없습니다.\" }")))
    })
    public ResponseEntity<?> scrapPost(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "스크랩할 게시글 ID와 사용자 ID",
            required = true,
            content = @Content(schema = @Schema(example = """
                        {
                            "postId": 5,
                            "userId": 7
                        }
                    """))
    ) Map<String, Integer> requestData) {

        int postId = requestData.get("postId");
        int userId = requestData.get("userId");

        // 게시글 및 유저 확인
        Optional<RecipeSharePost> optionalPost = recipeSharePostRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalPost.isEmpty() || optionalUser.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "해당 게시글 또는 유저를 찾을 수 없습니다."));
        }

        RecipeSharePost post = optionalPost.get();
        User user = optionalUser.get();


        RecipeLike newScrap = new RecipeLike();
        newScrap.setPost(post);
        newScrap.setUser(user);
        recipeLikeRepository.save(newScrap);

        post.setLikeCount(post.getLikeCount() + 1);
        recipeSharePostRepository.save(post);

        return ResponseEntity.ok(Map.of(
                "postId", post.getId(),
                "message", "스크랩이 추가되었습니다."
        ));
    }

    @GetMapping("/scrap/check")
    @Operation(
            summary = "스크랩 여부 확인",
            description = "특정 게시글이 특정 사용자가 스크랩했는지 여부를 확인합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 여부 조회 성공",
                    content = @Content(schema = @Schema(example = """
                        {
                            "postId": 4,
                            "userId": 5,
                            "scraped": false
                        }
                    """)))
    })
    public ResponseEntity<?> checkScrap(@RequestParam int postId, @RequestParam int userId) {
        boolean alreadyScraped = recipeLikeRepository.existsByPostIdAndUserId(postId, userId);
        return ResponseEntity.ok(Map.of(
                "postId", postId,
                "userId", userId,
                "scraped", alreadyScraped
        ));
    }


    @GetMapping("/recommend/user/{userId}")
    @Operation(
            summary = "사용자 맞춤 레시피 추천",
            description = "특정 사용자의 family 유형 및 age(나잇대) 기반으로 많이 스크랩된 게시글을 추천합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 리스트 반환",
                    content = @Content(schema = @Schema(example = """
                        {
                            "family": "1인가구",
                            "familyTypeRecommendations": [
                                {
                                    "id": 10,
                                    "title": "혼자서도 쉽게 만드는 파스타",
                                    "picture": "/uploads/pasta.jpg"
                                },
                                {
                                    "id": 5,
                                    "title": "간단한 계란요리",
                                    "picture": "/uploads/egg.jpg"
                                }
                            ],
                            "ageGroupRecommendations": [
                                {
                                    "id": 12,
                                    "title": "20대가 선호하는 샐러드",
                                    "picture": "/uploads/salad.jpg"
                                },
                                {
                                    "id": 8,
                                    "title": "10분 안에 완성하는 도시락",
                                    "picture": "/uploads/lunchbox.jpg"
                                }
                            ]
                        }
                    """)))
    })
    public ResponseEntity<?> recommendRecipes(@PathVariable int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "해당 userId를 찾을 수 없습니다."));
        }

        User user = optionalUser.get();
        String family = user.getFamily();
        int age = user.getAge();

        // 가족 유형 기반 추천 (TOP 5)
        List<Map<String, Object>> familyTypeRecommendations = recipeSharePostRepository.findTop5ByFamily(family)
                .stream().map(post -> {
                    Map<String, Object> postMap = new HashMap<>();
                    postMap.put("id", post.getId());
                    postMap.put("title", post.getTitle());
                    postMap.put("picture", post.getPicture());
                    return postMap;
                }).collect(Collectors.toList());


        List<Map<String, Object>> ageGroupRecommendations = recipeSharePostRepository.findTop5ByAge(age)
                .stream().map(post -> {
                    Map<String, Object> postMap = new HashMap<>();
                    postMap.put("id", post.getId());
                    postMap.put("title", post.getTitle());
                    postMap.put("picture", post.getPicture());
                    return postMap;
                }).collect(Collectors.toList());


        Map<String, Object> response = new HashMap<>();
        response.put("family", family);
        response.put("familyTypeRecommendations", familyTypeRecommendations);
        response.put("ageGroupRecommendations", ageGroupRecommendations);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}/details")
    @Operation(
            summary = "레시피 상세 정보 조회",
            description = "특정 레시피 게시글의 도구(tool), 조미료(seasoning), 레시피(recipe), 재료(ingredient) 정보를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "레시피 상세 조회 성공",
                    content = @Content(schema = @Schema(example = """
                        {
                            "tool": ["프라이팬", "주걱"],
                            "seasoning": ["소금", "간장"],
                            "recipe": ["김치를 볶는다", "햄을 추가한다", "밥을 넣고 볶는다"],
                            "ingredient": [
                                {
                                    "name": "김치",
                                    "imageUrl": "/uploads/kimchi.jpg"
                                },
                                {
                                    "name": "밥",
                                    "imageUrl": "/uploads/rice.jpg"
                                },
                                {
                                    "name": "햄"
                                }
                            ]
                        }
                    """)))
    })
    public ResponseEntity<?> getRecipeDetails(@PathVariable int postId) {
        Optional<RecipeSharePost> optionalPost = recipeSharePostRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "해당 postId를 찾을 수 없습니다."));
        }

        RecipeSharePost post = optionalPost.get();


        List<String> tool = post.getTool();

        List<String> seasoning = post.getSeasoning().stream()
                .map(s -> s.split(" ")[0])
                .collect(Collectors.toList());


        List<String> recipe = post.getRecipe();


        List<Map<String, String>> ingredient = post.getIngredient().stream()
                .map(i -> {
                    String name = i.split(" ")[0]; 
                    Ingredient foundIngredient = ingredientRepository.findByName(name);
                    Map<String, String> ingredientMap = new HashMap<>();
                    ingredientMap.put("name", name);


                    if (foundIngredient != null) {
                        ingredientMap.put("imageUrl", foundIngredient.getPicture());
                    }

                    return ingredientMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "tool", tool,
                "seasoning", seasoning,
                "recipe", recipe,
                "ingredient", ingredient
        ));
    }

}
