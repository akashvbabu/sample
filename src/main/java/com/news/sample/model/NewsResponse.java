package com.news.sample.model;

/**
 * POJO representing a news response
 */
public class NewsResponse {
    private long totalArticles;
    private NewsArticle[] articles;

    public long getTotalArticles() {
        return totalArticles;
    }

    public void setTotalArticles(long totalArticles) {
        this.totalArticles = totalArticles;
    }

    public NewsArticle[] getArticles() {
        return articles;
    }

    public void setArticles(NewsArticle[] articles) {
        this.articles = articles;
    }
}
