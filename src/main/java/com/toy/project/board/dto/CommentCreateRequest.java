package com.toy.project.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    Long articleId;
    Long parentId;
    String requestUserId;
    String body;
}
