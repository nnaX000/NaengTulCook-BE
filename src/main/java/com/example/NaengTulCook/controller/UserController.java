package com.example.NaengTulCook.controller;

import com.example.NaengTulCook.dto.UserSignUpRequest;
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

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "회원가입", description = "Create a new user and register them to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest request) {
        userService.signUp(request);
        // 사용자 가입 로직
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    // 로그인 API
    @Operation(summary = "로그인", description = "User login with userIdentifier and password")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공시에 user id를 반환한다.(앞으로 포함해야하는 id값)",
                    content = @Content(
                            schema = @Schema(implementation = Integer.class),
                            examples = @ExampleObject(value = "1") // 성공적인 로그인 예시값 (userId)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid login credentials",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Invalid credentials\"") // 로그인 실패 예시값
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Internal server error\"") // 서버 오류 예시값
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserSignUpRequest request) {
        Integer userId = userService.login(request.getUserIdentifier(), request.getPassword());

        if (userId != null) {
            return new ResponseEntity<>(userId, HttpStatus.OK); // 로그인 성공, 유저 id 반환
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST); // 로그인 실패
        }
    }

    // 닉네임 업데이트 API
    @Operation(summary = "닉네임 저장", description = "Update the nickname of the user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Nickname updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Nickname updated successfully\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID or nickname",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Invalid user ID or nickname\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Internal server error\"")
                    )
            )
    })
    @PutMapping("/update-nickname")
    public ResponseEntity<String> updateNickname(@RequestParam Integer userId, @RequestParam String nickname) {
        try {
            userService.updateNickname(userId, nickname); // 닉네임 업데이트
            return new ResponseEntity<>("Nickname updated successfully", HttpStatus.OK); // 성공
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid user ID or nickname", HttpStatus.BAD_REQUEST); // 실패
        }
    }

    // 나이대 업데이트 API
    @Operation(summary = "나이대 저장", description = "20대면 20대, 30대면 30대 이런식으로 int형으로 보내주세요.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Age range updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Age range updated successfully\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID or age range",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Invalid user ID or age range\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Internal server error\"")
                    )
            )
    })
    @PutMapping("/update-age-range")
    public ResponseEntity<String> updateAgeRange(@RequestParam Integer userId, @RequestParam int ageRange) {
        try {
            userService.updateAgeRange(userId, ageRange); // 나이대 업데이트
            return new ResponseEntity<>("Age range updated successfully", HttpStatus.OK); // 성공
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid user ID or age range", HttpStatus.BAD_REQUEST); // 실패
        }
    }

    // 가구 유형 업데이트 API
    @Operation(summary = "가구 유형 저장", description = "Update the family type of the user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Family type updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Family type updated successfully\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID or family type",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Invalid user ID or family type\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Internal server error\"")
                    )
            )
    })
    @PutMapping("/update-family")
    public ResponseEntity<String> updateFamily(@RequestParam Integer userId, @RequestParam String family) {
        try {
            userService.updateFamily(userId, family); // 가구 유형 업데이트
            return new ResponseEntity<>("Family type updated successfully", HttpStatus.OK); // 성공
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid user ID or family type", HttpStatus.BAD_REQUEST); // 실패
        }
    }

    // 요리 실력 업데이트 API
    @Operation(summary = "요리 실력 저장", description = "위 버튼부터 순서대로 0~4입니다. Int형으로 보내주세요. 완전처음 : 0 / 라면 끓이기 정도는 할 수 있어요 : 1")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cooking skill updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Cooking skill updated successfully\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID or cooking skill",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Invalid user ID or cooking skill\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Internal server error\"")
                    )
            )
    })
    @PutMapping("/update-skill")
    public ResponseEntity<String> updateSkill(@RequestParam Integer userId, @RequestParam int skill) {
        try {
            if (skill < 0 || skill > 4) {
                return new ResponseEntity<>("Invalid skill level. It should be between 0 and 4.", HttpStatus.BAD_REQUEST); // 유효한 범위 확인
            }
            userService.updateSkill(userId, skill); // 요리 실력 업데이트
            return new ResponseEntity<>("Cooking skill updated successfully", HttpStatus.OK); // 성공
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid user ID or cooking skill", HttpStatus.BAD_REQUEST); // 실패
        }
    }

    // 취향 업데이트 API
    @Operation(summary = "취향 저장", description = "Update the favorite preferences of the user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Favorite preferences updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Favorite preferences updated successfully\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID or favorite preferences",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Invalid user ID or favorite preferences\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Internal server error\"")
                    )
            )
    })
    @PutMapping("/update-favorite")
    public ResponseEntity<String> updateFavorite(@RequestParam Integer userId, @RequestBody List<String> favorite) {
        try {
            userService.updateFavorite(userId, favorite); // 취향 업데이트
            return new ResponseEntity<>("Favorite preferences updated successfully", HttpStatus.OK); // 성공
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid user ID or favorite preferences", HttpStatus.BAD_REQUEST); // 실패
        }
    }

    // 재료 업데이트 API
    @Operation(summary = "재료 저장", description = "Update the ingredients list of the user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ingredients updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Ingredients updated successfully\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID or ingredients",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Invalid user ID or ingredients\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Internal server error\"")
                    )
            )
    })
    @PutMapping("/update-ingredient")
    public ResponseEntity<String> updateIngredient(@RequestParam Integer userId, @RequestBody List<String> ingredients) {
        try {
            userService.updateIngredient(userId, ingredients); // 재료 업데이트
            return new ResponseEntity<>("Ingredients updated successfully", HttpStatus.OK); // 성공
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid user ID or ingredients", HttpStatus.BAD_REQUEST); // 실패
        }
    }

    // 닉네임 중복 확인 API
    @Operation(summary = "닉네임 중복 확인", description = "Check if the nickname is already taken")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Nickname is available",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Nickname is available\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Nickname is already taken",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Nickname is already taken\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Internal server error\"")
                    )
            )
    })
    @GetMapping("/check-nickname")
    public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
        boolean isTaken = userService.isNicknameTaken(nickname);

        if (isTaken) {
            return new ResponseEntity<>("Nickname is already taken", HttpStatus.BAD_REQUEST); // 이미 존재하는 경우
        } else {
            return new ResponseEntity<>("Nickname is available", HttpStatus.OK); // 사용 가능한 경우
        }
    }
}
