package it.aulab.chronicles.repositories;

import org.springframework.data.repository.ListCrudRepository;

import it.aulab.chronicles.models.Category;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {
    
}
