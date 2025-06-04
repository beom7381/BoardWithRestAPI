package com.toy.project.board.mapper;

import com.toy.project.global.interfaces.DtoToEntityMapperInterface;
import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleCreateMapper implements DtoToEntityMapperInterface<Article, ArticleCreateRequest> {
    @Override
    public Article toEntity(ArticleCreateRequest dto) {
        return Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
}
