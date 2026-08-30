package org.project.service;

import org.project.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RecipeService {
    private static ConcurrentHashMap<String, Recipe> recipes = new ConcurrentHashMap<>();

    public Recipe getRecipe(String recipeId) {
        return recipes.get(recipeId);
    }

    public Recipe createRecipe(Recipe recipe) {
        recipes.put(recipe.getId(), recipe);
        return recipe;
    }

    public List<Recipe> getAll() {
        return new ArrayList<>(recipes.values());
    }

    public void update(Recipe recipe) {
        recipes.put(recipe.getId(), recipe);
    }

    public void delete(String recipeId) {
        recipes.remove(recipeId);
    }
}
