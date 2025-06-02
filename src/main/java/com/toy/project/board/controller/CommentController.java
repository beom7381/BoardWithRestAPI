package com.toy.project.board.controller;

import com.toy.project.board.dto.CommentCreateRequest;
import com.toy.project.board.dto.CommentUpdateRequest;
import com.toy.project.board.exception.ArticleNotFoundException;
import com.toy.project.board.service.CommentService;
import com.toy.project.authorize.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/comment/{articleId}")
    public ResponseEntity<?> commentList(@PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok(commentService.getCommentList(articleId));
    }

    @PostMapping("/api/comment/")
    public ResponseEntity<?> writeComment(@RequestBody CommentCreateRequest commentCreateRequest) {
        try {
            return ResponseEntity.ok(commentService.createComment(commentCreateRequest));
        } catch (UserNotFoundException | ArticleNotFoundException exception) {
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/api/comment/{id}")
    public ResponseEntity<?> editComment(@RequestBody CommentUpdateRequest commentUpdateRequest) {
        try {
            return ResponseEntity.ok(commentService.updateComment(commentUpdateRequest));
        } catch (UserNotFoundException | ArticleNotFoundException exception) {
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        if(commentService.deleteComment(id)){
            return ResponseEntity.ok("success");
        }
        else{
            return ResponseEntity.internalServerError().body("fail");
        }
    }
}
