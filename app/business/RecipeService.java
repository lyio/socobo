package business;

import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;
import models.recipes.Recipe;

import java.util.List;
import java.util.Optional;

@ImplementedBy(RecipeServiceImpl.class)
public interface RecipeService {
    List<Recipe> findAllByOwner(long userId);

    Optional<Recipe> findSingleRecipe(long userId, long recipeId);

    Recipe createRecipe(Recipe r);
}
