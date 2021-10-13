package io.eagle44.allegrointern2021.repo;

import io.eagle44.allegrointern2021.util.LinkHeaderParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepoService {
    private final RepoClient repoClient;

    public RepoService(RepoClient repoClient) {
        this.repoClient = repoClient;
    }

    public Repository getRepositories(String username, int perPage, int page) {
        ResponseEntity<List<Repo>> response = repoClient.queryGithubApiForRepos(username, perPage, page);
        List<String> linkHeader = response.getHeaders().get(HttpHeaders.LINK);
        Pagination pagination = null;
        if (linkHeader != null) {
            pagination = LinkHeaderParser.parse(linkHeader);
        }
        return new Repository(response.getBody(), pagination);
    }
}
