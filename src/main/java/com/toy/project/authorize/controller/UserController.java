package com.toy.project.authorize.controller;

import com.toy.project.authorize.dto.SignInRequest;
import com.toy.project.authorize.dto.SignUpRequest;
import com.toy.project.authorize.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/list")
    public ResponseEntity<?> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @PostMapping("/api/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }

    @PostMapping("/api/user/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest,
                                    HttpServletResponse response) {
        return ResponseEntity.ok(userService.signIn(signInRequest, response));
    }

    @PostMapping("/api/user/signout")
    public ResponseEntity<?> signOut(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            HttpServletResponse response) {
        userService.signOut(accessToken, response);

        return ResponseEntity.ok().build();
    }

    /*
        1. 리프레시 /엑세스 토큰 생성
        2. 클라이언트가 엑세스 토큰으로 요청
        3. 서버에서(필터) 엑세스 토큰 유효성 검사
        4. 만료되었다면 401/403 반환
        5. 클라이언트는 401/403이 반환된 경우 /api/user/refresh 호출
        6. 서버는 쿠키에 있는 리프레시 토큰을 확인하고 유효하면 엑세스 토큰 새로 생성해서 반환
        7. 클라이언트는 새로운 엑세스 토큰을 헤더에 넣고 다시 요청
     */
    @PostMapping("/api/user/refresh")
    public ResponseEntity<?> refreshingToken(@RequestBody String userId,
                                             @CookieValue(name = "Refresh-Token", required = false) String refreshToken){
        return ResponseEntity.ok(userService.refreshingAccessToken(userId, refreshToken));
    }
}
