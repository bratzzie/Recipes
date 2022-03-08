package com.stud.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(@Autowired RecipeRepository repository) {
        this.repository = repository;
    }

    public Optional<Recipe> getRecipeById(Long id) throws ResponseStatusException {
        return repository.findById(id);
    }

    public Long postNewRecipe(Recipe recipe) {
        Recipe savedRecipe = repository.save(recipe);
        return savedRecipe.getId();
    }

    public ResponseEntity removeRecipeById(Long id) {
        Optional<Recipe> recipeToRemove = repository.findById(id);

        if (recipeToRemove.isPresent()) {
            recipeToRemove.ifPresent((recipe) -> repository.deleteById(id));
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity updateRecipeById(Long id, Recipe newRecipe) {
        Optional<Recipe> recipeToUpdate = getRecipeById(id);

        if (recipeToUpdate.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        newRecipe.setId(id);
        repository.save(newRecipe);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public List<Recipe> getByCategory(String category) {
        List<Recipe> recipes = StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(recipe -> recipe.getCategory()
                        .toLowerCase(Locale.ROOT)
                        .equals(category.toLowerCase(Locale.ROOT)))
                .sorted(Comparator.comparing(Recipe::getDate))
                .collect(Collectors.toList());
        Collections.reverse(recipes);
        return recipes;
    }

    public List<Recipe> getByName(String name) {
        List<Recipe> recipes = StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(recipe -> recipe.getName()
                        .toLowerCase(Locale.ROOT)
                        .contains(name.toLowerCase(Locale.ROOT)))
                .sorted(Comparator.comparing(Recipe::getDate))
                .collect(Collectors.toList());
        Collections.reverse(recipes);
        return recipes;
    }
}
