package io.eagle44.allegrointern2021.stargazers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/stargazers/")
public class StargazersController {

    private final StargazersService stargazersService;

    public StargazersController(StargazersService stargazersService) {
        this.stargazersService = stargazersService;
    }

    @GetMapping(value = "{username}")
    public Stargazers getStargazers(@PathVariable String username) {
        return stargazersService.getStargazers(username);
    }

}
