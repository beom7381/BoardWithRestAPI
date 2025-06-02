package com.toy.project.board.util;

import com.toy.project.interfaces.DtoToEntityMapperInterface;
import com.toy.project.interfaces.EntityToDtoMapperInterface;
import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.dto.ArticleResponse;
import com.toy.project.board.entity.Article;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ArticleMapperContainer {
    private final DtoToEntityMapperInterface<Article, ArticleCreateRequest> articleCreateMapper;
    private final EntityToDtoMapperInterface<Article, ArticleResponse> articleReadMapper;

    public ArticleMapperContainer(
            @Qualifier("articleCreateMapper") DtoToEntityMapperInterface<Article, ArticleCreateRequest> articleCreateMapper,
            @Qualifier("articleReadMapper") EntityToDtoMapperInterface<Article, ArticleResponse> articleReadMapper) {
        this.articleCreateMapper = articleCreateMapper;
        this.articleReadMapper = articleReadMapper;
    }
}
