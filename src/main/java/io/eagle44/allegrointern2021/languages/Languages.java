package io.eagle44.allegrointern2021.languages;

import java.util.*;
import java.util.stream.Collectors;

public class Languages {
    private final HashMap<String, Long> languages;

    public Languages() {
        languages = new HashMap<>();
    }

    public void addLanguages(Map<String, Long> languagesOfOneRepo) {
        for (String language : languagesOfOneRepo.keySet()) {
            Long currNumOfBytes = Objects.requireNonNullElse(languages.get(language), 0L);
            languages.put(language, currNumOfBytes + languagesOfOneRepo.get(language));
        }
    }

    public HashMap<String, Long> getLanguagesSortedDesc() {
        return languages.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
