package com.toy.project.board.util;

import com.toy.project.base.DtoToEntityMapperInterface;
import com.toy.project.board.dto.CommentCreateRequest;
import com.toy.project.board.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentCreateMapper implements DtoToEntityMapperInterface<Comment, CommentCreateRequest> {
    @Override
    public Comment toEntity(CommentCreateRequest dto) {
        return Comment.builder()
                .body(dto.getBody())
                .build();
    }
}
