package io.eagle44.allegrointern2021.stargazers;

import io.eagle44.allegrointern2021.repo.Pagination;
import io.eagle44.allegrointern2021.repo.Repo;
import io.eagle44.allegrointern2021.repo.RepoService;
import io.eagle44.allegrointern2021.repo.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StargazersServiceTest {

    @Mock
    private RepoService repoService;

    @InjectMocks
    private StargazersService stargazersService;

    @Test
    void shouldReturnCorrectAmountOfStarsForSampleList() {
        when(repoService.getRepository(anyString(), anyInt(), anyInt())).thenReturn(new Repository(getSampleListOfRepos(), null));
        assertEquals(13, stargazersService.getStargazers("foo").getStargazersCount());
    }

    @Test
    void shouldReturnCorrectAmountOfStarsForSampleListWithPagination() {
        List<Repo> repos = getSampleListOfRepos();

        Pagination pagination1 = new Pagination(null, "foo", null, "foo");
        Repository repository1 = new Repository(repos, pagination1);

        Pagination pagination2 = new Pagination("foo", "foo", "foo", "foo");
        Repository repository2 = new Repository(repos, pagination2);

        Pagination pagination3 = new Pagination("foo", null, "foo", null);
        Repository repository3 = new Repository(repos, pagination3);

        when(repoService.getRepository(anyString(), anyInt(), eq(1))).thenReturn(repository1);
        when(repoService.getRepository(anyString(), anyInt(), eq(2))).thenReturn(repository2);
        when(repoService.getRepository(anyString(), anyInt(), eq(3))).thenReturn(repository3);

        assertEquals(39, stargazersService.getStargazers("foo").getStargazersCount());
    }

    private List<Repo> getSampleListOfRepos() {
        return List.of(new Repo("foo1", 10),
                new Repo("foo2", 2), new Repo("foo3", 1));
    }
}