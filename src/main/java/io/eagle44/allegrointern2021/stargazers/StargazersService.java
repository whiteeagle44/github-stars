package io.eagle44.allegrointern2021.stargazers;

import io.eagle44.allegrointern2021.repo.Repo;
import io.eagle44.allegrointern2021.repo.RepoService;
import io.eagle44.allegrointern2021.repo.Repository;
import org.springframework.stereotype.Service;

@Service
public class StargazersService {
    RepoService repoService;

    public StargazersService(RepoService repoService) {
        this.repoService = repoService;
    }

    public Stargazers getStargazers(String username) {
        int i = 0;
        long sum = 0;
        Repository repository;
        do {
            i++;
            repository = repoService.getRepositories(username, 100, i);
            sum += repository.getRepo().stream().mapToLong(Repo::getStargazersCount).sum();
        } while(repository.getPagination() != null && repository.getPagination().getNextPage() != null);
        return new Stargazers(sum);
    }
}
