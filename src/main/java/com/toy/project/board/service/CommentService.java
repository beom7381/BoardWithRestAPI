package com.toy.project.board.service;

import com.toy.project.board.dto.CommentCreateRequest;
import com.toy.project.board.dto.CommentResponse;
import com.toy.project.board.dto.CommentUpdateRequest;
import com.toy.project.board.repository.ArticleRepository;
import com.toy.project.board.repository.CommentRepository;
import com.toy.project.board.util.CommentMapperContainer;
import com.toy.project.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        var requestUser = userRepository.findByUserId(commentCreateRequest.getRequestUserId());
        var article = articleRepository.findById(commentCreateRequest.getArticleId())
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        var comment = commentCreateMapper.toEntity(commentCreateRequest);
        var parentComment = commentRepository.findById(commentCreateRequest.getParentId()).orElse(null);

        comment.setArticle(article);
        comment.setWriter(requestUser);
        comment.setParent(parentComment);

        return commentReadMapper.toDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentResponse updateComment(CommentUpdateRequest commentUpdateRequest){
        var mapper = commentMapperContainer.getCommentReadMapper();
        var requestUser = userRepository.findByUserId(commentUpdateRequest.getRequestUserId());
        var targetComment = commentRepository.findById(commentUpdateRequest.getId()).orElse(null);

        if(targetComment != null){
            targetComment.setBody(commentUpdateRequest.getBody());
        }

        return mapper.toDto(targetComment);
    }

    public boolean deleteComment(Long id){
        try{
            var targetComment = commentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("삭제할 댓글이 존해하지 않습니다."));

            commentRepository.delete(targetComment);

            return true;
        }
        catch (Exception ex){
            return false;
        }
    }
}
