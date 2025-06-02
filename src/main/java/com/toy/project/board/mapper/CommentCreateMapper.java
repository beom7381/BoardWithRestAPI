package com.toy.project.board.mapper;

import com.toy.project.interfaces.DtoToEntityMapperInterface;
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
