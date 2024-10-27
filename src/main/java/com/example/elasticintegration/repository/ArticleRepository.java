package com.example.elasticintegration.repository;

import com.example.elasticintegration.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    Page<Article> findByAuthorsName(String name, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
    Page<Article> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);

    @Query("{\"fuzzy\": {\"title.keyword\": {\"value\": \"?0\", \"fuzziness\": \"2\"}}}")
    Page<Article> findByTitleWithFuzzySearch(String title, Pageable pageable);
}
