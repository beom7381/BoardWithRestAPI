package com.toy.project.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateRequest {
    private Long id;
    private String requestUserId;
    private String title;
    private String content;
}
