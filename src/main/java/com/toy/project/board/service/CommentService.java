package com.toy.project.board.service;

import com.toy.project.board.dto.CommentCreateRequest;
import com.toy.project.board.dto.CommentResponse;
import com.toy.project.board.dto.CommentUpdateRequest;
import com.toy.project.board.entity.Comment;
import com.toy.project.board.exception.AuthorizeNotMatchException;
import com.toy.project.board.exception.CommentNotFoundException;
import com.toy.project.board.repository.ArticleRepository;
import com.toy.project.board.repository.CommentRepository;
import com.toy.project.board.util.CommentMapperContainer;
import com.toy.project.authorize.exception.UserNotFoundException;
import com.toy.project.authorize.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentMapperContainer commentMapperContainer;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          ArticleRepository articleRepository,
                          CommentMapperContainer commentMapperContainer) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentMapperContainer = commentMapperContainer;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentList(Long articleId) {
        var mapper = commentMapperContainer.getCommentReadMapper();

        return commentRepository.findByArticleIdAndParentIsNullOrderByCreatedAtAsc(articleId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public CommentResponse createComment(CommentCreateRequest commentCreateRequest) {
        var commentCreateMapper = commentMapperContainer.getCommentCreateMapper();
        var commentReadMapper = commentMapperContainer.getCommentReadMapper();

        var requestUser = userRepository.findByUserId(commentCreateRequest.getRequestUserId())
                .orElseThrow(() -> new UserNotFoundException("유저정보가 잘못됨"));

        var article = articleRepository.findById(commentCreateRequest.getArticleId())
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없음"));

        var comment = commentCreateMapper.toEntity(commentCreateRequest);

        Comment parentComment = null;

        if (commentCreateRequest.getParentId() != null) {
            parentComment = commentRepository.findById(commentCreateRequest.getParentId()).orElse(null);
        }

        comment.setArticle(article);
        comment.setWriter(requestUser);
        comment.setParent(parentComment);

        return commentReadMapper.toDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentResponse updateComment(CommentUpdateRequest commentUpdateRequest) {
        var commentReadMapper = commentMapperContainer.getCommentReadMapper();

        var requestUser = userRepository.findByUserId(commentUpdateRequest.getRequestUserId())
                .orElseThrow(() -> new UserNotFoundException("유저정보가 잘못됨"));

        var targetComment = commentRepository.findById(commentUpdateRequest.getId())
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없음"));

        if(targetComment.getWriter().getUserId().equals(requestUser.getUserId())){
            targetComment.setBody(commentUpdateRequest.getBody());

            return commentReadMapper.toDto(targetComment);
        }
        else{
            throw new AuthorizeNotMatchException("본인외의 접근 시도");
        }
    }

    public boolean deleteComment(Long id) {
        try {
            var targetComment = commentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("삭제할 댓글이 존해하지 않음"));

            commentRepository.delete(targetComment);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
