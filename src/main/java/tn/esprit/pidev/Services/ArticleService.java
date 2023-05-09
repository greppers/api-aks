package tn.esprit.pidev.Services;

import lombok.AllArgsConstructor;
import tn.esprit.pidev.Entities.Article;
import tn.esprit.pidev.Entities.Keyword;
import tn.esprit.pidev.Repositories.ArticleRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import tn.esprit.pidev.Repositories.KeywordRepository;

@Service
public class ArticleService {
    ArticleRepository repository;

    @Autowired
    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
	// this.keywordRepository = keywordRepository;
    }

    public List<Article> all() {
	return (List<Article>) repository.findAll();
    }
    
    public Article get(Long id) {
	return repository.findById(id).get();
    }
    
    public Article update(Article article) {
	return repository.save(article);
    }

    public void delete(Long id) {
	repository.deleteById(id);
    }
    
    public Article create(Article article) {
	return repository.save(article);
    }
    
//     public Article assignKeyword(UUID articleId, Long keywordId) {
// 	Article article = repository.findById(articleId).get();
// 	Optional<Keyword> keyword = keywordRepository.findById(keywordId);
// 	if (!keyword.isPresent()) {
// 	    article.getKeywords().add(keyword.get());
// 	}
// 	repository.save(article);
// 	return article;
//     }
}
