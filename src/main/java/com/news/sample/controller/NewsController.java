package com.news.sample.controller;

import com.news.sample.model.NewsResponse;
import com.news.sample.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for the news API for our service
 */
@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<NewsResponse> getNews(@RequestParam(required = false) Optional<String> keyword, @RequestParam(required = false) Optional<String> title) {
        return new ResponseEntity<>(newsService.getNews(keyword, title), HttpStatus.OK);
    }

    @GetMapping("/news/headlines")
    public ResponseEntity<NewsResponse> getHeadlines() {
        return new ResponseEntity<>(newsService.getHeadlines(), HttpStatus.OK);
    }
}