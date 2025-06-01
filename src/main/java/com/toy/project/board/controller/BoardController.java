package com.toy.project.board.controller;

import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.dto.ArticleUpdateRequest;
import com.toy.project.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/board/article/create")
    public ResponseEntity<?> writeArticle(@RequestBody ArticleCreateRequest articleCreateRequest){
        return ResponseEntity.ok(boardService.createArticle(articleCreateRequest));
    }

    @PatchMapping("/api/board/article/update")
    public ResponseEntity<?> editArticle(@RequestParam ArticleUpdateRequest articleUpdateRequest){
        return ResponseEntity.ok(boardService.updateArticle(articleUpdateRequest));
    }

    @DeleteMapping("/api/board/article/delete")
    public ResponseEntity<?> removeArticle(){
        return ResponseEntity.ok("");
    }
}
