package com.toy.project.board.controller;

import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.dto.ArticleUpdateRequest;
import com.toy.project.board.service.ArticleService;
import com.toy.project.global.annotation.RequireAccessToken;
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
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @RequireAccessToken
    @PostMapping("/api/articles")
    public ResponseEntity<?> writeArticle(@RequestBody ArticleCreateRequest articleCreateRequest) {
        return ResponseEntity.ok(articleService.createArticle(articleCreateRequest));
    }

    @RequireAccessToken
    @PatchMapping("/api/articles")
    public ResponseEntity<?> editArticle(@RequestBody ArticleUpdateRequest articleUpdateRequest) {
        return ResponseEntity.ok(articleService.updateArticle(articleUpdateRequest));
    }

    @RequireAccessToken
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<?> removeArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);

        return ResponseEntity.ok().build();
    }
}
