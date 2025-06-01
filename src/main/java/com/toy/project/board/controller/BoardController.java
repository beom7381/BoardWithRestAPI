package com.toy.project.board.controller;

import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/api/board/page={page}")
    public ResponseEntity<?> getArticleList(@RequestParam("page") int page){
        return ResponseEntity.ok(boardService.getArticleList(page));
    }

    @GetMapping("/api/board/article/{id}")
    public ResponseEntity<?> getArticle(@RequestParam("id") Long id){
        return ResponseEntity.ok(boardService.getArticle(id));
    }

    @PostMapping("/api/board/article/new")
    public ResponseEntity<?> writeArticle(@RequestBody ArticleCreateRequest articleCreateRequest){
        return ResponseEntity.ok(boardService.createArticle(articleCreateRequest));
    }
}
