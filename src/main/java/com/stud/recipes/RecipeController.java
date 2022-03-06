package com.stud.recipes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {
    Recipe currentRec;

    @GetMapping("/api/recipe")
    public Recipe getRecipe() {
        return currentRec;
    }

    @PostMapping("/api/recipe")
    public void postRecipe(@RequestBody Recipe recipe) {
        currentRec = recipe;
    }
}
