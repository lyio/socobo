package biz.recipe;

import datalayer.RecipeRepository;
import datalayer.UserRepository;
import models.recipes.Recipe;
import models.user.User;
import org.joda.time.DateTime;

import javax.inject.Inject;

public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    @Inject
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public Recipe addRecipe(String userName, Recipe recipe) {
        recipe.owner = userRepository.findByUserName(userName);
        recipe.createdAt = DateTime.now();
        return recipeRepository.save(recipe);
    }
}
