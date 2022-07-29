package com.emettely.cookingtime.spring.data.mongodb.controller;

import com.emettely.cookingtime.pojo.RecipeDTO;
import com.emettely.cookingtime.service.RecipeService;
import com.emettely.cookingtime.spring.data.mongodb.model.Recipe;
import com.emettely.cookingtime.spring.data.mongodb.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// – @CrossOrigin is for configuring allowed origins.
//– @RestController annotation is used to define a controller and to indicate that the return value of the methods should be be bound to the web response body.
//– @RequestMapping("/api") declares that all Apis’ url in the controller will start with /api.
//– We use @Autowired to inject RecipeRepository bean to local variable.
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    @GetMapping("/recipes")
    public ResponseEntity<Map<String, Object>> getAllRecipes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String url,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        List<RecipeDTO> recipes;

        try {
            Page<RecipeDTO> recipePage = recipeService.getRecipePage(title, url, page, size);
            recipes = recipePage.getContent();
            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return mapRecipesToResponseEntity(recipes, recipePage);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Map<String, Object>> mapRecipesToResponseEntity(List<RecipeDTO> recipes, Page<RecipeDTO> recipePage) {
        Map<String, Object> response = new HashMap<>();
        response.put("recipes", recipes);
        response.put("currentPage", recipePage.getNumber());
        response.put("totalItems", recipePage.getTotalElements());
        response.put("totalPages", recipePage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") String id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        return optionalRecipe
                .map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDTO recipe) {
        try {
            Recipe _recipe = recipeService.createRecipe(recipe);
            return new ResponseEntity<>(_recipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") String id, @RequestBody RecipeDTO recipe) {
        Recipe result = recipeService.updateRecipe(id, recipe);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") String id) {
        try {
            recipeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/recipes")
    public ResponseEntity<HttpStatus> deleteAllRecipes() {
        try {
            recipeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipes/published")
    public ResponseEntity<Map<String, Object>> findByPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            Page<RecipeDTO> recipePage = recipeService.findByPublished(true, page, size);
            List<RecipeDTO> recipes = recipePage.getContent();
            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return mapRecipesToResponseEntity(recipes, recipePage);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/web-recipe")
    public ResponseEntity<Recipe> webRecipe(@RequestBody String url) {
        Recipe _recipe;
        url = url.trim();
        try {
            if (url.isBlank()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                _recipe = recipeRepository.findByUrl(url);
                if (_recipe != null) {
                    return new ResponseEntity<>(_recipe, HttpStatus.NOT_MODIFIED);
                }

            }
            return new ResponseEntity<>(_recipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
