package com.toy.project.board.mapper;

import com.toy.project.interfaces.EntityToDtoMapperInterface;
import com.toy.project.board.dto.ArticleResponse;
import com.toy.project.board.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleReadMapper implements EntityToDtoMapperInterface<Article, ArticleResponse> {
    @Override
    public ArticleResponse toDto(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .writer(article.getWriter())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }
    /*
        protected Long id;
    protected User writer;
    protected String title;
    protected String content;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

     */
}
