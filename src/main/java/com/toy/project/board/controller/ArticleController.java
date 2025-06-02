package com.toy.project.board.controller;

import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.dto.ArticleUpdateRequest;
import com.toy.project.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/api/article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable("id") Long id){
        return ResponseEntity.ok(boardService.getArticle(id));
    }

    @PostMapping("/api/article/create")
    public ResponseEntity<?> writeArticle(@RequestBody ArticleCreateRequest articleCreateRequest){
        return ResponseEntity.ok(boardService.createArticle(articleCreateRequest));
    }

    @PatchMapping("/api/article/update")
    public ResponseEntity<?> editArticle(@RequestBody ArticleUpdateRequest articleUpdateRequest){
        return ResponseEntity.ok(boardService.updateArticle(articleUpdateRequest));
    }

    @DeleteMapping("/api/article/delete/{id}")
    public ResponseEntity<?> removeArticle(@PathVariable("id") Long id){
        return ResponseEntity.ok(boardService.deleteArticle(id));
    }
}
