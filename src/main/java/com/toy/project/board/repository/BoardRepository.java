package com.toy.project.board.repository;

import com.toy.project.board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Article, Long> {
    Optional<Article> findById(Long id);
}
