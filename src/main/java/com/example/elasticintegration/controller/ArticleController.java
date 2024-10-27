package com.example.elasticintegration.controller;

import com.example.elasticintegration.model.Article;
import com.example.elasticintegration.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/bulk")
    public ResponseEntity<Void> saveArticles(@RequestBody List<Article> articles) {
        articleService.saveArticles(articles);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteArticle(@RequestBody Article article) {
        articleService.deleteArticle(article);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/authors")
    public ResponseEntity<Page<Article>> findByAuthorsName(@RequestParam String name, Pageable pageable) {
        Page<Article> articles = articleService.findByAuthorsName(name, pageable);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/authors/custom")
    public ResponseEntity<Page<Article>> findByAuthorsNameUsingCustomQuery(@RequestParam String name, Pageable pageable) {
        Page<Article> articles = articleService.findByAuthorsNameUsingCustomQuery(name, pageable);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/title/fuzzy")
    public ResponseEntity<Page<Article>> findByTitleWithFuzzySearch(@RequestParam String title, Pageable pageable) {
        Page<Article> articles = articleService.findByTitleWithFuzzySearch(title, pageable);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/title/fuzzy/operations")
    public ResponseEntity<SearchHits<Article>> findByTitleWithFuzzySearchByOperations(@RequestParam String title, Pageable pageable) {
        SearchHits<Article> searchHits = articleService.findByTitleWithFuzzySearchByOperations(title, pageable);
        return ResponseEntity.ok(searchHits);
    }
}