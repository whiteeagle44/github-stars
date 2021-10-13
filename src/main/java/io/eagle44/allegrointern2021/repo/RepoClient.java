package io.eagle44.allegrointern2021.repo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.util.List;

@Component
public class RepoClient {
    private final WebClient.Builder builder;

    @Value("${github-token}")
    private String githubToken;

    public RepoClient(WebClient.Builder builder) {
        this.builder = builder;
    }

    public ResponseEntity<List<Repo>> queryGithubApiForRepos(String username, int perPage, int page) throws ResponseStatusException {
        return builder
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubToken)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true)))
                .build()
                .get()
                .uri(buildRepoQueryUri(username, perPage, page))
                .retrieve()
                .toEntityList(Repo.class)
                .block();
    }

    private URI buildRepoQueryUri(String username, int perPage, int page) {
        String repoQueryUri = "https://api.github.com/users/" + username + "/repos" + "?per_page=" + perPage + "&page=" + page;
        return URI.create(repoQueryUri);
    }

}
