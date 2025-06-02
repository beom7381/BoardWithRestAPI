package com.toy.project.board.dto;

import com.toy.project.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    protected Long id;
    protected User writer;
    protected String title;
    protected String content;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
