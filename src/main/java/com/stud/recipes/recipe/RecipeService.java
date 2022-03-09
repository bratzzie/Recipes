package com.stud.recipes.recipe;

import com.stud.recipes.dao.RecipeRepository;
import com.stud.recipes.dao.UserRepository;
import com.stud.recipes.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeService {
    private final RecipeRepository repository;
    private final UserRepository userRepository;

    public RecipeService(@Autowired RecipeRepository repository, @Autowired UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Optional<Recipe> getRecipeById(Long id) throws ResponseStatusException {
        return repository.findById(id);
    }

    public Long postNewRecipe(Recipe recipe) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName());

        recipe.setUser(user);
        Recipe savedRecipe = repository.save(recipe);
        return savedRecipe.getId();
    }

    public ResponseEntity removeRecipeById(Long id) {
        Optional<Recipe> recipeToRemove = repository.findById(id);

        if (recipeToRemove.isPresent()) {
            User userOfRecipe = recipeToRemove.get().getUser();
            //   System.out.println(userOfRecipe.getEmail());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = auth.getName();

            if (userOfRecipe == null) {
                repository.deleteById(id);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

            if (userOfRecipe.getEmail().equals(currentUserName)) {
                Set<Recipe> recipes = userOfRecipe.getUsersRecipes();
                recipes.remove(recipeToRemove.get());
                userOfRecipe.setUsersRecipes(recipes);
                userRepository.findUserByEmail(currentUserName).setUsersRecipes(recipes);
                recipeToRemove.get().setUser(null);
                repository.deleteById(id);

                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity updateRecipeById(Long id, Recipe newRecipe) {
        Optional<Recipe> recipeToUpdate = getRecipeById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = auth.getName();

        if (recipeToUpdate.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (recipeToUpdate.get().getUser().getEmail().equals(currentUserName)) {
            newRecipe.setId(id);
            repository.save(newRecipe);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
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
