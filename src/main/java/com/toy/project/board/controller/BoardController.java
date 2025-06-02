package com.toy.project.board.controller;

import com.toy.project.board.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BoardController {
    private final ArticleService boardService;

    public BoardController(ArticleService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/api/board")
    public ResponseEntity<?> getArticleList(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(boardService.getArticleList(page, size));
    }
}
