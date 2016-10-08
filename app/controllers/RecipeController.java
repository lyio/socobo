package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import datalayer.RecipeRepository;
import models.recipes.Recipe;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Optional;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

@Singleton
public class RecipeController extends Controller {

    private final RecipeRepository recipeRepository;

    @Inject
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Renders list of recipes as json
     * @return
     */
    public Result list(long userId) {
        return ok(toJson(recipeRepository.findAllByOwner(userId)));
    }

    public Result show(long userId, long recipeId) {

        final Optional<Recipe> maybeRecipe = recipeRepository.findByOwnerAndId(userId, recipeId);
        return maybeRecipe.isPresent() ? ok(toJson(maybeRecipe.get())) : notFound();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result save(Long userId) {
        Recipe r = fromJson(request().body().asJson(), Recipe.class);

        return created(toJson(recipeRepository.save(r)));
    }
}
