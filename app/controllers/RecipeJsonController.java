package controllers;

import com.google.gson.GsonBuilder;
import models.Recipe;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class RecipeJsonController extends Controller {

    /**
     * Renders list of recipes as html
     *
     * @return
     */
    public static Result list() {
        final List<Recipe> productList = Recipe.findAll();

        return ok(new GsonBuilder().create().toJson(productList));
    }

    public static Result newProduct() {
        return TODO;
    }

    public static Result details(final String ean) {
        return TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result save() {
        return TODO;
    }
}
