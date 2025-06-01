package com.toy.project.board.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ArticleMapperContainer {
    private final ArticleCreateMapper articleCreateMapper;
    private final ArticleReadMapper articleReadMapper;

    public ArticleMapperContainer(){
        this.articleCreateMapper = new ArticleCreateMapper();
        this.articleReadMapper = new ArticleReadMapper();
    }
}
