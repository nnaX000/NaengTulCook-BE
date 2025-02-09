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
}
