package com.toy.project.board.util;

import com.toy.project.base.EntityToDtoMapperInterface;
import com.toy.project.board.dto.ArticleResponse;
import com.toy.project.board.entity.Article;

public class ArticleUpdateMapper implements EntityToDtoMapperInterface<Article, ArticleResponse> {
    @Override
    public ArticleResponse toDto(Article entity) {
        return new ArticleResponse(
                entity.getId(),
                entity.getWriter(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
