package io.eagle44.allegrointern2021.repo;

import io.eagle44.allegrointern2021.util.LinkHeaderParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RepoService {
    private final RepoClient repoClient;

    public RepoService(RepoClient repoClient) {
        this.repoClient = repoClient;
    }

    public Repository getRepository(String username, int perPage, int page) {
        ResponseEntity<List<Repo>> response = repoClient.queryGithubApiForRepos(username, perPage, page).block();

        List<String> linkHeader = response.getHeaders().get(HttpHeaders.LINK);
        Pagination pagination = LinkHeaderParser.parse(linkHeader);

        return new Repository(response.getBody(), pagination);
    }

    public List<Repo> getAllRepos(String username) {
        ResponseEntity<List<Repo>> response = repoClient.queryGithubApiForRepos(username, 100, 1).block();
        List<String> linkHeader = response.getHeaders().get(HttpHeaders.LINK);
        int pages = 1;
        if (!Objects.isNull(linkHeader)) {
             pages = LinkHeaderParser.parseNumberOfPages(linkHeader);
        }

        List<Repo> repos = new ArrayList<>(pages * 100);
        if (!Objects.isNull(response.getBody())) {
            repos.addAll(response.getBody());
        }

        Flux.range(2, pages - 1)// start from 2nd page because we already processed 1st page to get num of pages
                .flatMap(i -> repoClient.queryGithubApiForRepos(username, 100, i))
                .doOnNext(reposOfOnePage -> {
                    if (!Objects.isNull(reposOfOnePage.getBody())) {
                        repos.addAll(reposOfOnePage.getBody());
                    }
                })
                .blockLast();
        return repos;
    }
}
