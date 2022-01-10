package io.eagle44.allegrointern2021.stargazers;

import io.eagle44.allegrointern2021.repo.Repo;
import io.eagle44.allegrointern2021.repo.RepoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StargazersService {
    RepoService repoService;

    public StargazersService(RepoService repoService) {
        this.repoService = repoService;
    }

    public Stargazers getStargazers(String username) {
        List<Repo> repos = repoService.getAllRepos(username);
        long sum = 0;
        if (!Objects.isNull(repos)) {
            sum += repos.stream().mapToLong(Repo::getStargazersCount).sum();
        }
        return new Stargazers(sum);
    }
}
