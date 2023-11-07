package com.news.sample.service;

import com.news.sample.config.NewsFilterConfig;
import com.news.sample.model.NewsResponse;

import java.util.Optional;

/**
 * Interface representing a NewsService
 */
public interface NewsService {

    NewsResponse getNews(Optional<String> keyword, Optional<String> title);

    NewsResponse getHeadlines();
}
