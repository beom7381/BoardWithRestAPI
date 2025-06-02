package com.toy.project.authorize.controller;

import com.toy.project.authorize.dto.SignInRequest;
import com.toy.project.authorize.dto.SignUpRequest;
import com.toy.project.authorize.exception.DuplicateIdException;
import com.toy.project.authorize.exception.DuplicateNameException;
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
        try {
            return ResponseEntity.ok(userService.getUserList());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류");
        }
    }

    @PostMapping("/api/user/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try{
            log.info(signUpRequest.toString());
            var response = userService.signUp(signUpRequest);

            return ResponseEntity.ok(response);
        } catch (DuplicateIdException | DuplicateNameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류");
        }
    }

    @PostMapping("/api/user/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        //Authorization 추가해야함
        try {
            return ResponseEntity.ok(userService.signIn(signInRequest));
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("아이디 혹은 비밀번호가 잘못됨");
        }
    }

    @PostMapping("api/user/signout")
    public ResponseEntity<?> signOut(@RequestBody String token, HttpSession session){
        userService.signOut(session);

        return ResponseEntity.ok("로그아웃 완료");
    }
}
