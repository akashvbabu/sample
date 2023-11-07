package com.news.sample.controller;

import com.news.sample.exception.InvalidNewsFilterException;
import com.news.sample.model.NewsArticle;
import com.news.sample.model.NewsResponse;
import com.news.sample.service.NewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link NewsController}
 */
@WebMvcTest(NewsController.class)
public class NewsControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Test
    void newsRetrievedSuccessfully() throws Exception {
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setContent("test content");
        newsArticle.setDescription("test description");
        newsArticle.setTitle("test title");
        NewsArticle[] articles = new NewsArticle[1];
        articles[0] = newsArticle;
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setTotalArticles(1L);
        newsResponse.setArticles(articles);
        when(newsService.getNews(Optional.of("example"), Optional.empty())).thenReturn(newsResponse);
        this.mockMvc
                .perform((get("/news").param("keyword", "example")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test content")))
                .andExpect(content().string(containsString("test description")))
                .andExpect(content().string(containsString("test title")))
                .andExpect(content().string(containsString("\"totalArticles\":1")));;
    }

    @Test
    void headlinesRetrievedSuccessfully() throws Exception {
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setContent("headlines content");
        newsArticle.setDescription("headlines description");
        newsArticle.setTitle("headlines title");
        NewsArticle[] articles = new NewsArticle[1];
        articles[0] = newsArticle;
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setTotalArticles(1L);
        newsResponse.setArticles(articles);
        when(newsService.getHeadlines()).thenReturn(newsResponse);
        this.mockMvc
                .perform(get("/news/headlines"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("headlines content")))
                .andExpect(content().string(containsString("headlines description")))
                .andExpect(content().string(containsString("headlines title")))
                .andExpect(content().string(containsString("\"totalArticles\":1")));;
    }

    @Test
    void newsBadRequest() throws Exception {
        when(newsService.getNews(any(), any())).thenThrow(InvalidNewsFilterException.class);
        this.mockMvc
                .perform((get("/news")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
