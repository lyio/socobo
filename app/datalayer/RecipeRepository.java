package datalayer;

import com.avaje.ebean.Finder;
import com.google.inject.ImplementedBy;
import models.recipes.Recipe;

import java.util.List;
import java.util.Optional;

@ImplementedBy(RecipeRepositoryImpl.class)
public interface RecipeRepository {
    Finder<String, Recipe> finder = new Finder<>(Recipe.class);

    Recipe save(Recipe r);

    Optional<Recipe> findByOwnerAndId(Long userId, Long recipeId);

    List<Recipe> findAllByOwner(Long userId);
}
