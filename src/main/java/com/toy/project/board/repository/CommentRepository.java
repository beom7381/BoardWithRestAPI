package com.toy.project.board.repository;

import com.toy.project.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleIdAndParentIsNullOrderByCreatedAtAsc(Long articleId);
    List<Comment> findByParentIdOrderByCreatedAtAsc(Long parentId);
}
