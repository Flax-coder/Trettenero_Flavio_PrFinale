package it.aulab.chronicles.controllers.api;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import it.aulab.chronicles.dtos.api.ArticleApiDto;
import it.aulab.chronicles.services.ArticleService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {

    private final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleApiDto> index() {
        return articleService.readAllApi();
    }

    @GetMapping("/{id}")
    public ArticleApiDto show(@PathVariable Long id) {
        return articleService.readApi(id);
    }
}

