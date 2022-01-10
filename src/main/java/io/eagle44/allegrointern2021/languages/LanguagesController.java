package io.eagle44.allegrointern2021.languages;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/languages/")
public class LanguagesController {

    private final LanguagesService languagesService;

    public LanguagesController(LanguagesService languagesService) {
        this.languagesService = languagesService;
    }

    @GetMapping(value = "{username}")
    public Languages getLanguages(@PathVariable String username, @RequestParam(defaultValue = "100") String repos) {
        return languagesService.getLanguages(username, Integer.parseInt(repos));
    }

}

