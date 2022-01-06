package io.eagle44.allegrointern2021.languages;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LanguagesService {
    private final LanguagesClient languagesClient;

    public LanguagesService(LanguagesClient languagesClient) {
        this.languagesClient = languagesClient;
    }

    public Languages getLanguages(String username, int repos) {
        Languages languages = new Languages();
        List<String> languagesUrls = getLanguagesUrls(username, repos);
        for (String languagesUrl : languagesUrls) {
            Map<String, Long> response = languagesClient.queryGithubApiForLanguages(username, languagesUrl, repos, 1).getBody();
            if(!Objects.isNull(response)) {
                languages.addLanguages(response);
            }
        }
        return languages;
    }

    public List<String> getLanguagesUrls(String username, int repos) {
        ResponseEntity<List<LanguagesUrl>> response = languagesClient.queryGithubApiForLanguagesUrls(username, repos, 1);
        List<String> languagesUrlList = response.getBody().stream().map(LanguagesUrl::getLanguagesUrl).collect(Collectors.toList());
        return Objects.requireNonNullElse(languagesUrlList, Collections.emptyList());
    }
}
