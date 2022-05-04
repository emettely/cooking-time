package com.emettely.cookingtime.spring.data.mongodb.controller;

import com.emettely.cookingtime.spring.data.mongodb.model.Recipe;
import com.emettely.cookingtime.spring.data.mongodb.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emettely.cookingtime.http.APOD;
import com.emettely.cookingtime.http.JsonBodyHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.function.Supplier;

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

    @GetMapping("/recipes")
    public ResponseEntity<Map<String, Object>> getAllRecipes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String url,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        List<Recipe> recipes;

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Recipe> recipePage;
            if (title != null) {
                recipePage = recipeRepository.findByTitleContainingIgnoreCase(title, paging);
            } else if (url != null) {
                recipePage = recipeRepository.findByUrlStartsWith(url, paging);
            } else {
                recipePage = recipeRepository.findAll(paging);
            }

            recipes = recipePage.getContent();

            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("recipes", recipes);
            response.put("currentPage", recipePage.getNumber());
            response.put("totalItems", recipePage.getTotalElements());
            response.put("totalPages", recipePage.getTotalPages());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") String id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        return optionalRecipe
                .map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        try {
            String url = recipe.getUrl();

            Recipe _recipe = recipeRepository.save(
                    new Recipe(recipe.getTitle(), recipe.getDescription(), false, url)
            );

            if (url != null) {
                HttpClient client = HttpClient.newHttpClient();
                URI uri = URI.create(url);
                HttpRequest request = HttpRequest.newBuilder(uri).header("accept", "application/json").build();
                HttpResponse<Supplier<APOD>> response = client.send(request, new JsonBodyHandler<>(APOD.class));
                System.out.println(response.body().get());
            }

            return new ResponseEntity<>(_recipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") String id, @RequestBody Recipe recipe) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe _recipe = optionalRecipe.get();
            _recipe.setTitle(recipe.getTitle());
            _recipe.setDescription(recipe.getDescription());
            _recipe.setPublished(recipe.isPublished());
            return new ResponseEntity<>(recipeRepository.save(_recipe), HttpStatus.OK);
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
            Pageable paging = PageRequest.of(page, size);
            Page<Recipe> recipePage = recipeRepository.findByPublished(true, paging);
            List<Recipe> recipes = recipePage.getContent();
            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("recipes", recipes);
                response.put("currentPage", recipePage.getNumber());
                response.put("totalItems", recipePage.getTotalElements());
                response.put("totalPages", recipePage.getTotalPages());

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
