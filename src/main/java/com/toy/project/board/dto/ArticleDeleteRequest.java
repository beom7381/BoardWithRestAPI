package com.toy.project.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDeleteRequest {
    private Long id;
    private String requestUserId;
}
