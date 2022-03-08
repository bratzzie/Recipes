package com.stud.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipeController {
    private final RecipeService service;

    public RecipeController(@Autowired RecipeService service) {
        this.service = service;
    }

    @GetMapping("/api/recipe/{id}")
    public Map<String, Object> getRecipe(@PathVariable Long id) throws ResponseStatusException {

        Optional<Recipe> recipeToFind = service.getRecipeById(id);

        if (recipeToFind.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Map<String, Object> recipeToReturn = new LinkedHashMap<>();
        recipeToReturn.put("name", recipeToFind.get().getName());
        recipeToReturn.put("description", recipeToFind.get().getDescription());
        recipeToReturn.put("ingredients", recipeToFind.get().getIngredients());
        recipeToReturn.put("directions", recipeToFind.get().getDirections());
        return recipeToReturn;
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> postRecipe( @Valid @RequestBody Recipe recipe) {
        Long index = service.postNewRecipe(recipe);

        Map<String, Long> indexToReturn = new LinkedHashMap<>();
        indexToReturn.put("id", index);
        return indexToReturn;
    }

    @DeleteMapping("/api/recipe/{id}")
    public HttpStatus removeRecipe(@PathVariable Long id) {
        return service.removeRecipeById(id);
    }
}