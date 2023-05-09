package tn.esprit.pidev.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.esprit.pidev.Services.ArticleService;
import tn.esprit.pidev.Entities.Article;

@RestController
@RequestMapping("/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

    ArticleService service;

    public ArticleController() {}

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.service = articleService;
    }
    
    @GetMapping
    public List<Article> all() {
        return service.all();
    }

    @GetMapping("{id}")
    public Article get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @PutMapping
    public Article update(@RequestBody Article article) {
	return service.update(article);
    }

    @PostMapping
    public Article create(@RequestBody Article article) {
	return service.create(article);
    }
}
