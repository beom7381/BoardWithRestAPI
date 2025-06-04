package com.toy.project.authorize.controller;

import com.toy.project.authorize.dto.SignInRequest;
import com.toy.project.authorize.dto.SignUpRequest;
import com.toy.project.authorize.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/user/list")
    public ResponseEntity<?> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @PostMapping("/api/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }

    @PostMapping("/api/user/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(userService.signIn(signInRequest));
    }

    @PostMapping("api/user/signout")
    public ResponseEntity<?> signOut(@RequestBody String token, HttpSession session) {
        userService.signOut(session);

        return ResponseEntity.ok().build();
    }
}
