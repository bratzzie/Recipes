package com.stud.recipes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Vector;

@RestController
public class RecipeController {
    Vector<Recipe> recipes = new Vector<>(15);

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {

        if (id >= recipes.size())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Recipe recipeToFind = recipes.get(id);
        if (recipeToFind == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return recipeToFind;
    }

    @PostMapping("/api/recipe/new")
    public Index postRecipe(@RequestBody Recipe recipe) {
        recipes.add(recipe);
        return new Index(recipes.indexOf(recipe));
    }
}