package com.toy.project.board.util;

import com.toy.project.base.DtoToEntityMapperInterface;
import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.entity.Article;

public class ArticleCreateMapper implements DtoToEntityMapperInterface<Article, ArticleCreateRequest> {
    @Override
    public Article toEntity(ArticleCreateRequest dto) {
        return new Article(
                null,
                null,
                dto.getTitle(),
                dto.getContent(),
                null,
                null
        );
    }
}
