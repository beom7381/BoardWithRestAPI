package com.toy.project.user.controller;

import com.toy.project.user.dto.SignInRequest;
import com.toy.project.user.dto.SignUpRequest;
import com.toy.project.user.exception.DuplicateIdException;
import com.toy.project.user.exception.DuplicateNameException;
import com.toy.project.user.service.UserService;
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
        return ResponseEntity.ok(userService.signIn(signInRequest));
    }

    @PostMapping("api/user/signout")
    public ResponseEntity<?> signOut(@RequestBody String token, HttpSession session){
        userService.signOut(session);

        return ResponseEntity.ok("로그아웃 완료");
    }
}
