package it.aulab.chronicles.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import it.aulab.chronicles.models.Article;
import it.aulab.chronicles.models.Category;
import it.aulab.chronicles.models.User;

public interface ArticleRepository extends ListCrudRepository<Article, Long> {
    List<Article> findByCategory(Category category);
    List<Article> findByUser(User user);
}
