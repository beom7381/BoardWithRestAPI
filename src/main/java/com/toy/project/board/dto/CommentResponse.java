package com.toy.project.board.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String writerName;
    private String body;
    private LocalDateTime createdAt;
    private List<CommentResponse> children;
}
