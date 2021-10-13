package io.eagle44.allegrointern2021.repo;

import java.util.List;

public class Repository {
    private List<Repo> repo;
    private Pagination pagination;

    public Repository() {
    }

    public Repository(List<Repo> repo, Pagination pagination) {
        this.repo = repo;
        this.pagination = pagination;
    }

    public List<Repo> getRepo() {
        return repo;
    }

    public void setRepo(List<Repo> repo) {
        this.repo = repo;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
