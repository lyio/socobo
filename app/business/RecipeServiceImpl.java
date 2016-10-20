package business;

import com.google.inject.Inject;
import datalayer.RecipeRepository;
import models.recipes.Recipe;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Inject
    @SuppressWarnings("unused")
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAllByOwner(long userId) {
        return recipeRepository.findAllByOwner(userId);
    }

    @Override
    public Optional<Recipe> findSingleRecipe(long userId, long recipeId) {
        return recipeRepository.findByOwnerAndId(userId, recipeId);
    }

    @Override
    public Recipe createRecipe(Recipe r) {
        return recipeRepository.save(r);
    }
}
