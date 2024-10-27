package com.example.elasticintegration.service;

import com.example.elasticintegration.model.Article;
import com.example.elasticintegration.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public void saveArticles(List<Article> articles) {
        List<IndexQuery> indexQueries = articles.stream()
                .map(article -> {
                    IndexQuery query = new IndexQuery();
                    query.setId(article.getId());
                    query.setObject(article);
                    return query;
                })
                .toList();

        elasticsearchOperations.bulkIndex(indexQueries, Article.class);
    }

    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }

    public Page<Article> findByAuthorsName(String name, Pageable pageable) {
        return articleRepository.findByAuthorsName(name, pageable);
    }

    public Page<Article> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable) {
        return articleRepository.findByAuthorsNameUsingCustomQuery(name, pageable);
    }

    public Page<Article> findByTitleWithFuzzySearch(String title, Pageable pageable) {
        return articleRepository.findByTitleWithFuzzySearch(title, pageable);
    }

    public SearchHits<Article> findByTitleWithFuzzySearchByOperations(String title, Pageable pageable) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .fuzzy(f -> f
                                .field("title.keyword")
                                .value(title)
                                .fuzziness("2")
                        )
                )
                .withPageable(pageable)
                .build();

        return elasticsearchOperations.search(query, Article.class);
    }
}
