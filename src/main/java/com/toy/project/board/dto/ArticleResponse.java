package com.toy.project.board.dto;

import com.toy.project.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    protected Long id;
    protected User writer;
    protected String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
