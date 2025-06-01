package com.toy.project.board.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ArticleMapperContainer {
    private final ArticleCreateMapper articleCreateMapper;
    private final ArticleReadMapper articleReadMapper;
    private final ArticleUpdateMapper articleUpdateMapper;

    public ArticleMapperContainer(){
        this.articleCreateMapper = new ArticleCreateMapper();
        this.articleReadMapper = new ArticleReadMapper();
        this.articleUpdateMapper = new ArticleUpdateMapper();
    }
}
