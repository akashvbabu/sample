package com.news.sample.config;

import java.util.Optional;

/**
 * Filter that represents available query parameters from the API to use in our operations
 */
public class NewsFilterConfig {
    private Optional<String> keyword;
    private Optional<String> title;

    public NewsFilterConfig(Optional<String> keyword, Optional<String> title) {
        this.keyword = keyword;
        this.title = title;
    }

    public Optional<String> getKeyword() {
        return keyword;
    }

    public Optional<String> getTitle() {
        return title;
    }
}
