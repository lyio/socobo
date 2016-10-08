package datalayer;

import models.recipes.Recipe;

import java.util.List;
import java.util.Optional;

import static com.avaje.ebean.Expr.eq;

public class RecipeRepositoryImpl implements RecipeRepository {

    @Override
    public Recipe save(Recipe recipe) {
        finder.db().save(recipe);
        return recipe;
    }

    @Override
    public Optional<Recipe> findByOwnerAndId(Long userId, Long recipeId) {
        final Recipe recipe = finder
                .query()
                .where()
                .and(eq("owner.id", userId), eq("id", recipeId))
                .findUnique();
        return Optional.ofNullable(recipe);
    }

    @Override
    public List<Recipe> findAllByOwner(Long userId) {
        return finder
                .query()
                .where()
                .eq("owner.id", userId)
                .findList();
    }
}