package controllers;

import business.RecipeService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.recipes.Recipe;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Optional;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

@Singleton
public class RecipeController extends Controller {

    private final RecipeService recipeService;

    @Inject
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Renders list of recipes as json
     * @return
     */
    public Result list() {
        long userId = 0;
        return ok(toJson(recipeService.findAllByOwner(userId)));
    }

    public Result show(long recipeId) {

        long userId = 0;
        final Optional<Recipe> maybeRecipe = recipeService.findSingleRecipe(userId, recipeId);
        return maybeRecipe.isPresent() ? ok(toJson(maybeRecipe.get())) : notFound();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        long userId = 0;
        Recipe r = fromJson(request().body().asJson(), Recipe.class);

        return created(toJson(recipeService.createRecipe(r)));
    }
}
