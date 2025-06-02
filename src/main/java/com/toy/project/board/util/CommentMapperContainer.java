package com.toy.project.board.util;

import com.toy.project.interfaces.DtoToEntityMapperInterface;
import com.toy.project.interfaces.EntityToDtoMapperInterface;
import com.toy.project.board.dto.CommentCreateRequest;
import com.toy.project.board.dto.CommentResponse;
import com.toy.project.board.entity.Comment;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CommentMapperContainer {
    private final DtoToEntityMapperInterface<Comment, CommentCreateRequest> commentCreateMapper;
    private final EntityToDtoMapperInterface<Comment, CommentResponse> commentReadMapper;

    public CommentMapperContainer(
            @Qualifier("commentCreateMapper") DtoToEntityMapperInterface<Comment, CommentCreateRequest> commentCreateMapper,
            @Qualifier("commentReadMapper") EntityToDtoMapperInterface<Comment, CommentResponse> commentReadMapper) {
        this.commentCreateMapper = commentCreateMapper;
        this.commentReadMapper = commentReadMapper;
    }
}
