package controllers;

import models.Recipe;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class RecipeController extends Controller {

    /**
     * Renders list of recipes as html
     * @return
     */
    public static Result list() {
        final List<Recipe> productList = Recipe.findAll();
        return ok(views.html.recipes.list.render(productList));
    }

    public static Result newProduct() {
        return TODO;
    }

    public static Result details(final String ean) {
        return TODO;
    }

    public static Result save() {
        return TODO;
    }
}
