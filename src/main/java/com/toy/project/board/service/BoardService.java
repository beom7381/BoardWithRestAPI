package com.toy.project.board.service;

import com.toy.project.board.dto.ArticleCreateRequest;
import com.toy.project.board.dto.ArticleDeleteRequest;
import com.toy.project.board.dto.ArticleResponse;
import com.toy.project.board.dto.ArticleUpdateRequest;
import com.toy.project.board.entity.Article;
import com.toy.project.board.repository.BoardRepository;
import com.toy.project.board.util.ArticleMapperContainer;
import com.toy.project.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ArticleMapperContainer articleMapperContainer;

    public BoardService(BoardRepository boardRepository,
                        UserRepository userRepository,
                        ArticleMapperContainer articleMapperContainer){
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.articleMapperContainer = articleMapperContainer;
    }

    public Page<ArticleResponse> getArticleList(int page){
        var articleReadMapper = articleMapperContainer.getArticleReadMapper();

        int pageSize = 10;
        var pageRequest = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());

        return boardRepository.findAll(pageRequest).map(articleReadMapper::toDto);
    }

    public ArticleResponse getArticle(Long id){
        var articleReadMapper = articleMapperContainer.getArticleReadMapper();

        Article article = boardRepository.findById(id).orElse(null);

        if(article != null){
            return articleReadMapper.toDto(article);
        }
        else{
            return null;
        }
    }

    public ArticleResponse createArticle(ArticleCreateRequest articleCreateRequest){
        var articleCreateMapper = articleMapperContainer.getArticleCreateMapper();
        var articleReadMapper = articleMapperContainer.getArticleReadMapper();

        var newArticle = articleCreateMapper.toEntity(articleCreateRequest);
        newArticle.setWriter(userRepository.findByUserId(articleCreateRequest.getWriterId()));

        var wroteArticle = boardRepository.save(newArticle);

        return articleReadMapper.toDto(wroteArticle);
    }

    @Transactional
    public ArticleResponse updateArticle(ArticleUpdateRequest articleUpdateRequest){
        var articleUpdateMapper = articleMapperContainer.getArticleUpdateMapper();
        var targetArticle = boardRepository.findById(articleUpdateRequest.getId()).orElse(null);
        var requestUser = userRepository.findByUserId(articleUpdateRequest.getRequestUserId());

        if(targetArticle != null){
            if(targetArticle.getWriter().getId().equals(requestUser.getId())){
                targetArticle.setTitle(articleUpdateRequest.getTitle());
                targetArticle.setContent(articleUpdateRequest.getContent());

                return articleUpdateMapper.toDto(targetArticle);
            }
        }

        return null;
    }

    public boolean deleteArticle(ArticleDeleteRequest articleDeleteRequest){
        var targerArticle = boardRepository.findById(articleDeleteRequest.getId()).orElse(null);
        var requestUser = userRepository.findByUserId(articleDeleteRequest.getRequestUserId());

        if(targerArticle != null){
            if(targerArticle.getWriter().getId().equals(requestUser.getId())){
                boardRepository.delete(targerArticle);

                return true;
            }
        }

        return false;
    }
}
