package com.toy.project.user.controller;

import com.toy.project.user.dto.SignInRequest;
import com.toy.project.user.dto.SignUpRequest;
import com.toy.project.user.dto.SignUpResponse;
import com.toy.project.user.exception.DuplicateIdException;
import com.toy.project.user.exception.DuplicateNameException;
import com.toy.project.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/api/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try{
            log.info(signUpRequest.toString());
            SignUpResponse signUpResponse = userService.signUp(signUpRequest);

            return ResponseEntity.ok(signUpResponse);
        } catch (DuplicateIdException | DuplicateNameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류");
        }
    }

    @PostMapping("/api/user/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(userService.signIn(signInRequest));
    }
}
