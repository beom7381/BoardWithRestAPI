package com.toy.project.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateRequest {
    String writerId;
    String title;
    String content;
}
