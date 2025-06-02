package com.toy.project.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequest {
    private Long id;
    private String requestUserId;
    private String body;
}
