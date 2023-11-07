package com.news.sample.service;

import com.news.sample.config.NewsFilterConfig;
import com.news.sample.exception.InvalidNewsFilterException;
import com.news.sample.model.NewsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Implementation for {@link NewsService} use GNews API
 */
@Service
public class NewsServiceImpl implements NewsService {

    private final WebClient webClient;
    private static final String SEARCH_PATH = "/search";
    private static final String HEADLINES_PATH = "/top-headlines";
    @Value("${API_KEY}")
    private String API_KEY;

    private final String DEFAULT_LANG = "en";

    public NewsServiceImpl(@Value("${NEWS_API}") String newsAPI) {
        this.webClient = WebClient.builder().baseUrl(newsAPI).build();
    }

    @Override
    @Cacheable("articles")
    public NewsResponse getNews(Optional<String> keyword, Optional<String> title) {
        // if filter is empty (no keyword or title provided)
        if ((keyword.isEmpty() && title.isEmpty()) ||
                (keyword.isPresent() && keyword.get().isBlank()
                && title.isPresent() && title.get().isBlank())) {
            throw new InvalidNewsFilterException("either keyword or title has to be provided and cannot be empty/blank");
        }

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(SEARCH_PATH)
                        .queryParams(constructParametersFromFilters(keyword, title))
                        .build())
                .retrieve()
                .bodyToMono(NewsResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();
    }

    @Cacheable("headlines")
    @Override
    public NewsResponse getHeadlines() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(HEADLINES_PATH)
                        .queryParams(constructParametersFromFilters(Optional.empty(), Optional.empty()))
                        .build())
                .retrieve()
                .bodyToMono(NewsResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();
    }

    /**
     *
     * @param keyword to use in search
     * @param title to use in search
     * @return a query param map for use in http requests
     */
    public MultiValueMap<String, String> constructParametersFromFilters(Optional<String> keyword, Optional<String> title) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        // defaults for the API call to GNews
        multiValueMap.add("apikey", API_KEY);
        multiValueMap.add("lang", DEFAULT_LANG);

        // if both keyword and filter are provided, use an OR operator and use all available fields to search
        // else check if keyword or filter is present exclusively and use relevant fields to search
        if (keyword.isPresent() && !keyword.get().isBlank()
                && title.isPresent() && !title.get().isBlank()) {
            multiValueMap.add("q",
                    String.format("\"%s\" OR \"%s\"", keyword.get(), title.get()));
            multiValueMap.add("in", "title,description,content");
        } else if (keyword.isPresent() && !keyword.get().isBlank()
                && title.isEmpty()) {
            multiValueMap.add("q", String.format("%s", keyword.get()));
            multiValueMap.add("in", "description,content");
        } else if (keyword.isEmpty()
                && title.isPresent() && !title.get().isBlank()) {
            multiValueMap.add("q", String.format("%s", title.get()));
            multiValueMap.add("in", "title");
        }
        return multiValueMap;
    }
}

