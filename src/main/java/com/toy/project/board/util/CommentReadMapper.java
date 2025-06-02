package com.toy.project.board.util;

import com.toy.project.base.EntityToDtoMapperInterface;
import com.toy.project.board.dto.CommentResponse;
import com.toy.project.board.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentReadMapper implements EntityToDtoMapperInterface<Comment, CommentResponse> {
    @Override
    public CommentResponse toDto(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .writerName(comment.getWriter().getUserName())
                .body(comment.getBody())
                .createdAt(comment.getCreatedAt())
                .children(comment.getChildren().stream().map(this::toDto).toList())
                .build();
    }
}
