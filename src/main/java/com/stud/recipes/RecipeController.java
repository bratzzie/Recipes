package com.stud.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
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
        recipeToReturn.put("category", recipeToFind.get().getCategory());
        recipeToReturn.put("date", recipeToFind.get().getDate());
        recipeToReturn.put("description", recipeToFind.get().getDescription());
        recipeToReturn.put("ingredients", recipeToFind.get().getIngredients());
        recipeToReturn.put("directions", recipeToFind.get().getDirections());
        return recipeToReturn;
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe, BindingResult bindingResult) throws ResponseStatusException {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Long index = service.postNewRecipe(recipe);

        Map<String, Long> indexToReturn = new LinkedHashMap<>();
        indexToReturn.put("id", index);
        return indexToReturn;
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity removeRecipe(@PathVariable Long id) {
        return service.removeRecipeById(id);
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity updateRecipe(@PathVariable Long id,
                                       @Valid @RequestBody Recipe newRecipe,
                                       BindingResult bindingResult) throws ResponseStatusException {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return service.updateRecipeById(id, newRecipe);
    }

    @GetMapping("/api/recipe/search/")
    public ResponseEntity<List<Recipe>> search(@RequestParam Map<String,String> allRequestParams) {
        if (allRequestParams.size() == 1) {
            return allRequestParams.get("category") != null ? new ResponseEntity<>(service.getByCategory(allRequestParams.get("category")), HttpStatus.OK)
                    : allRequestParams.get("name") != null ? new ResponseEntity<>(service.getByName(allRequestParams.get("name")), HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}