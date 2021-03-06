package io.eagle44.allegrointern2021.languages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Component
public class LanguagesClient {
    private final WebClient.Builder builder;

    @Value("${github-token}")
    private String githubToken;

    public LanguagesClient(WebClient.Builder builder) {
        this.builder = builder;
    }

    public Mono<ResponseEntity<Map<String, Long>>> queryGithubApiForLanguages(String username, String languageUrl) throws ResponseStatusException {
        return builder
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubToken)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true)))
                .build()
                .get()
                .uri(languageUrl)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, Long>>() {});
    }

    public ResponseEntity<List<LanguagesUrl>> queryGithubApiForLanguagesUrls(String username, int perPage, int page) throws ResponseStatusException {
        return builder
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubToken)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true)))
                .build()
                .get()
                .uri(buildLanguagesUrlQueryUri(username, perPage, page))
                .retrieve()
                .toEntityList(LanguagesUrl.class)
                .block();
    }

    private URI buildLanguagesUrlQueryUri(String username, int perPage, int page) {
        String repoQueryUri = "https://api.github.com/users/" + username + "/repos" + "?per_page=" + perPage + "&page=" + page;
        return URI.create(repoQueryUri);
    }

}
