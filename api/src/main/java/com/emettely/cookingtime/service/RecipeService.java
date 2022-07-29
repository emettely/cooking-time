package com.emettely.cookingtime.service;

import com.emettely.cookingtime.pojo.RecipeDTO;
import com.emettely.cookingtime.spring.data.mongodb.model.Recipe;
import com.emettely.cookingtime.spring.data.mongodb.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeWebService recipeWebService;
    @Autowired
    RecipeMapperService recipeMapperService;

    public Page<RecipeDTO> getRecipePage(String title, String url, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<RecipeDTO> recipePage;
        if (title != null) {
            recipePage = recipeRepository.findByTitleContainingIgnoreCase(title, paging).map(recipe -> recipeMapperService.toRecipeDTO(recipe));
        } else if (url != null) {
            recipePage = recipeRepository.findByUrlStartsWith(url, paging).map(recipe -> recipeMapperService.toRecipeDTO(recipe));
        } else {
            recipePage = recipeRepository.findAll(paging).map(recipe -> recipeMapperService.toRecipeDTO(recipe));
        }

        return recipePage;
    }

    public Recipe createRecipe(RecipeDTO recipe) {
        String url = recipe.getUrl();
        Recipe _recipe = recipeRepository.save(
                new Recipe(recipe.getTitle(), recipe.getDescription(), false, url)
        );
        RecipeDTO result = recipeWebService.getRecipeFromUrl(url);
        return updateRecipe(_recipe.getId(), result);
    }

    public Recipe updateRecipe(String id, Recipe payload) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe _recipe = optionalRecipe.get();
            _recipe.setTitle(payload.getTitle());
            _recipe.setDescription(payload.getDescription());
            _recipe.setPublished(payload.isPublished());
            return recipeRepository.save(_recipe);
        } else {
            return null;
        }
    }

    public Recipe updateRecipe(String id, RecipeDTO payload) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe _recipe = optionalRecipe.get();
            _recipe.setTitle(payload.getTitle());
            _recipe.setDescription(payload.getDescription());
            _recipe.setPublished(payload.isPublished());
            return recipeRepository.save(_recipe);
        } else {
            return null;
        }
    }

    public Page<RecipeDTO> findByPublished(boolean published, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return recipeRepository.findByPublished(published, paging).map(recipe -> recipeMapperService.toRecipeDTO(recipe));
    }

    public RecipeDTO findDTOById(String id) {
        return recipeRepository.findById(id)
                .map(recipe -> recipeMapperService.toRecipeDTO(recipe)).orElse(null);
    }

    public Optional<Recipe> findById(String id) {
        return recipeRepository.findById(id);
    }
}
