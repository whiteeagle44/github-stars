package io.eagle44.allegrointern2021.languages;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LanguagesUrl {
    private String languagesUrl;

    public LanguagesUrl() {
    }

    public String getLanguagesUrl() {
        return languagesUrl;
    }
}
