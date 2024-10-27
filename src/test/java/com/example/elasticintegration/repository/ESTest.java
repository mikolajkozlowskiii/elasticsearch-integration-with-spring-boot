package com.example.elasticintegration.repository;


import com.example.elasticintegration.model.Article;
import com.example.elasticintegration.model.Author;
import com.example.elasticintegration.service.ArticleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ESTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleService articleService;
    private Article article1, article2;

    @BeforeEach
    void setUp() {
        article1 = new Article("1", "Spring Data Elasticsearch", List.of(new Author("John Doe")));
        article2 = new Article("2", "Advanced Elasticsearch Techniques", List.of(new Author("Jane Smith"),
                new Author("John Cena")));

        articleRepository.save(article1);
        articleRepository.save(article2);
    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteAll();
    }

    @Test
    void testFindByAuthorsName() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Article> result = articleRepository.findByAuthorsName("Jane", pageable);

        assertThat(result).isNotEmpty();
        result.forEach(s -> System.out.println(s));
    }

    @Test
    void testFindByAuthorsNam2e() {
        Pageable pageable = PageRequest.of(0, 10);
        final String title = "Advanced RlasRicsearch Techniques";
        Page<Article> result = articleRepository.findByTitleWithFuzzySearch(title, pageable);

        assertThat(result).isNotEmpty();
        result.forEach(System.out::println);
    }

    @Test
    void testFindByAuthorsNameUsingCustomQuery() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Article> result = articleRepository.findByAuthorsNameUsingCustomQuery("John", pageable);

        assertThat(result).isNotEmpty();
        result.forEach(System.out::println);
    }

    @Test
    void operationsTest() {
        Pageable pageable = PageRequest.of(0, 10);
        final String title = "Advanced RlasRicsearch Techniques";

        SearchHits<Article> hits = articleService.findByTitleWithFuzzySearchByOperations(title, pageable);

        System.out.println(hits);
        hits.getSearchHits().forEach(System.out::println);
    }

    @Test
    void bulkTest() {
        final Article article1 = new Article("100", "ES client tutorial", List.of(new Author("Walter White")));
        final Article article2 = new Article("101", "Solr Indexing Master IV", List.of(new Author("Saul Goodman"),
                new Author("John Cena")));

        final List<Article> articles = List.of(article1, article2);
        articleService.saveArticles(articles);
    }
}