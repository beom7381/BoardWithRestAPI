package com.toy.project.board.controller;

import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.dto.ArticleUpdateRequest;
import com.toy.project.board.exception.ArticleNotFoundException;
import com.toy.project.board.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/api/articles")
    public ResponseEntity<?> getArticleList(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.getArticleList(page, size));
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<?> getArticle(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(articleService.getArticle(id));
        } catch (ArticleNotFoundException exception) {
            return ResponseEntity.badRequest().body("게시글을 찾을 수 없음");
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/api/articles")
    public ResponseEntity<?> writeArticle(@RequestBody ArticleCreateRequest articleCreateRequest) {
        try {
            return ResponseEntity.ok(articleService.createArticle(articleCreateRequest));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/api/articles")
    public ResponseEntity<?> editArticle(@RequestBody ArticleUpdateRequest articleUpdateRequest) {
        try {
            return ResponseEntity.ok(articleService.updateArticle(articleUpdateRequest));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<?> removeArticle(@PathVariable("id") Long id) {
        if(articleService.deleteArticle(id)){
            return ResponseEntity.ok("success");
        }
        else{
            return ResponseEntity.internalServerError().build();
        }
    }
}
