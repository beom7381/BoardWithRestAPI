package com.toy.project.board.mapper;

import com.toy.project.board.dto.CommentResponse;
import com.toy.project.board.entity.Comment;
import com.toy.project.global.interfaces.EntityToDtoMapperInterface;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CommentReadMapper implements EntityToDtoMapperInterface<Comment, CommentResponse> {
    @Override
    public CommentResponse toDto(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .writerName(comment.getWriter().getNickName())
                .body(comment.getBody())
                .createdAt(comment.getCreatedAt())
                .children(
                        comment.getChildren() == null ?
                        Collections.emptyList() :
                        comment.getChildren().stream().map(this::toDto).toList())
                .build();
    }
}
