package com.news.sample.service;

import com.news.sample.config.NewsFilterConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

/**
 * Unit tests for {@link NewsServiceImpl}
 */
public class NewsServiceTests {

    private final NewsServiceImpl newsService = new NewsServiceImpl("http://example-server");

    @Test
    public void testQueryParametersKeyword() {
        MultiValueMap<String, String> queryParamMap = newsService.constructParametersFromFilters(Optional.of("example"), Optional.empty());
        Assertions.assertThat(queryParamMap.size()).isEqualTo(4);
        Assertions.assertThat(queryParamMap).containsKey("apikey");
        Assertions.assertThat(queryParamMap).containsKey("lang");
        Assertions.assertThat(queryParamMap).containsKey("q");
        Assertions.assertThat(queryParamMap).containsKey("in");
        Assertions.assertThat(queryParamMap.get("q")).contains("example");
        Assertions.assertThat(queryParamMap.get("in")).contains("description,content");
    }

    @Test
    public void testQueryParametersTitle() {
        MultiValueMap<String, String> queryParamMap = newsService.constructParametersFromFilters(Optional.empty(), Optional.of("example"));
        Assertions.assertThat(queryParamMap.size()).isEqualTo(4);
        Assertions.assertThat(queryParamMap).containsKey("apikey");
        Assertions.assertThat(queryParamMap).containsKey("lang");
        Assertions.assertThat(queryParamMap).containsKey("q");
        Assertions.assertThat(queryParamMap).containsKey("in");
        Assertions.assertThat(queryParamMap.get("q")).contains("example");
        Assertions.assertThat(queryParamMap.get("in")).contains("title");
    }

    @Test
    public void testQueryParametersKeywordTitle() {
        MultiValueMap<String, String> queryParamMap = newsService.constructParametersFromFilters(Optional.of("example"), Optional.of("example"));
        Assertions.assertThat(queryParamMap.size()).isEqualTo(4);
        Assertions.assertThat(queryParamMap).containsKey("apikey");
        Assertions.assertThat(queryParamMap).containsKey("lang");
        Assertions.assertThat(queryParamMap).containsKey("q");
        Assertions.assertThat(queryParamMap).containsKey("in");
        Assertions.assertThat(queryParamMap.get("q")).contains("\"example\" OR \"example\"");
        Assertions.assertThat(queryParamMap.get("in")).contains("title,description,content");
    }

    @Test
    public void testQueryParametersKeywordTitleEmpty() {
        MultiValueMap<String, String> queryParamMap = newsService.constructParametersFromFilters(Optional.empty(), Optional.empty());
        Assertions.assertThat(queryParamMap.size()).isEqualTo(2);
        Assertions.assertThat(queryParamMap).containsKey("apikey");
        Assertions.assertThat(queryParamMap).containsKey("lang");
    }
}
