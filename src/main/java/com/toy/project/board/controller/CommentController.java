package com.toy.project.board.controller;

import com.toy.project.board.dto.CommentCreateRequest;
import com.toy.project.board.dto.CommentUpdateRequest;
import com.toy.project.board.service.CommentService;
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
        return ResponseEntity.ok(commentService.createComment(commentCreateRequest));
    }

    @PatchMapping("/api/comment/{id}")
    public ResponseEntity<?> editComment(@RequestBody CommentUpdateRequest commentUpdateRequest) {
        return ResponseEntity.ok(commentService.updateComment(commentUpdateRequest));
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);

        return ResponseEntity.ok().build();
    }
}
