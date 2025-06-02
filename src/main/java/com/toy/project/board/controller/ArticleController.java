package com.toy.project.board.controller;

import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.dto.ArticleUpdateRequest;
import com.toy.project.board.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/api/article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable("id") Long id){
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @PostMapping("/api/article")
    public ResponseEntity<?> writeArticle(@RequestBody ArticleCreateRequest articleCreateRequest){
        return ResponseEntity.ok(articleService.createArticle(articleCreateRequest));
    }

    @PatchMapping("/api/article")
    public ResponseEntity<?> editArticle(@RequestBody ArticleUpdateRequest articleUpdateRequest){
        return ResponseEntity.ok(articleService.updateArticle(articleUpdateRequest));
    }

    @DeleteMapping("/api/article/{id}")
    public ResponseEntity<?> removeArticle(@PathVariable("id") Long id){
        return ResponseEntity.ok(articleService.deleteArticle(id));
    }
}
