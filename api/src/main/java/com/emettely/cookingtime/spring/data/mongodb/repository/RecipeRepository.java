package com.emettely.cookingtime.spring.data.mongodb.repository;

import java.util.List;

import com.emettely.cookingtime.spring.data.mongodb.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

// Now we can use MongoRepository’s methods:
// save(), findOne(), findById(), findAll(), count(), delete(), deleteById()…
// without implementing these methods.
// The implementation is plugged in by Spring Data MongoDB automatically.
public interface RecipeRepository extends MongoRepository<Recipe, String> {
    List<Recipe> findByTitleContaining(String title);

    Page<Recipe> findByPublished(boolean published, Pageable paging);

    Page<Recipe> findByTitleContainingIgnoreCase(String title, Pageable paging);

    Page<Recipe> findByUrlStartsWith(String url, Pageable paging);
}
