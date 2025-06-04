package com.toy.project.board.service;

import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.dto.ArticleResponse;
import com.toy.project.board.dto.ArticleUpdateRequest;
import com.toy.project.board.entity.Article;
import com.toy.project.global.exception.DeleteException;
import com.toy.project.global.exception.RequestEntityNotFoundException;
import com.toy.project.global.exception.AuthorNotMatchException;
import com.toy.project.board.repository.ArticleRepository;
import com.toy.project.board.util.ArticleMapperContainer;
import com.toy.project.global.exception.UserNotFoundException;
import com.toy.project.authorize.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapperContainer articleMapperContainer;

    public ArticleService(ArticleRepository boardRepository,
                          UserRepository userRepository,
                          ArticleMapperContainer articleMapperContainer) {
        this.articleRepository = boardRepository;
        this.userRepository = userRepository;
        this.articleMapperContainer = articleMapperContainer;
    }

    public Page<ArticleResponse> getArticleList(int page, int size) {
        var articleReadMapper = articleMapperContainer.getArticleReadMapper();

        var pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return articleRepository.findAll(pageRequest).map(articleReadMapper::toDto);
    }

    public ArticleResponse getArticle(Long id) {
        var articleReadMapper = articleMapperContainer.getArticleReadMapper();

        Article article = articleRepository.findById(id).orElse(null);

        if (article != null) {
            return articleReadMapper.toDto(article);
        } else {
            return null;
        }
    }

    public ArticleResponse createArticle(ArticleCreateRequest articleCreateRequest) {
        var articleCreateMapper = articleMapperContainer.getArticleCreateMapper();
        var articleReadMapper = articleMapperContainer.getArticleReadMapper();

        var newArticle = articleCreateMapper.toEntity(articleCreateRequest);
        var writer = userRepository.findByUserId(articleCreateRequest.getRequestUserId())
                        .orElseThrow(UserNotFoundException::new);

        newArticle.setWriter(writer);

        var wroteArticle = articleRepository.save(newArticle);

        return articleReadMapper.toDto(wroteArticle);
    }

    @Transactional
    public ArticleResponse updateArticle(ArticleUpdateRequest articleUpdateRequest) {
        var articleReadMapper = articleMapperContainer.getArticleReadMapper();

        var targetArticle = articleRepository.findById(articleUpdateRequest.getId())
                .orElseThrow(RequestEntityNotFoundException::new);

        var requestUser = userRepository.findByUserId(articleUpdateRequest.getRequestUserId())
                .orElseThrow(UserNotFoundException::new);

        if (targetArticle.getWriter().getId().equals(requestUser.getId())) {
            targetArticle.setTitle(articleUpdateRequest.getTitle());
            targetArticle.setContent(articleUpdateRequest.getContent());

            return articleReadMapper.toDto(targetArticle);
        } else {
            throw new AuthorNotMatchException();
        }
    }

    public void deleteArticle(Long id) {
        try{
            articleRepository.findById(id).ifPresent(articleRepository::delete);
        }
        catch (Exception exception){
            throw new DeleteException();
        }
    }
}
