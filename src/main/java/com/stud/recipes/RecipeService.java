package com.stud.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(@Autowired RecipeRepository repository) {
        this.repository = repository;
    }

    public Optional<Recipe> getRecipeById(Long id) throws ResponseStatusException {
      //  if (id >= repository.count())
      //      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       return repository.findById(id);
    }

    public Long postNewRecipe(Recipe recipe) {
        Recipe savedRecipe = repository.save(recipe);
        return savedRecipe.getId();
    }

    public HttpStatus removeRecipeById(Long id) {
        Optional<Recipe> recipeToRemove = repository.findById(id);

        if (recipeToRemove.isEmpty())
            return HttpStatus.NOT_FOUND;

        recipeToRemove.ifPresent((recipe) -> repository.deleteById(id));
        return HttpStatus.NO_CONTENT;
    }

}
