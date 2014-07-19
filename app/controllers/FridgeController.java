package controllers;


import models.fridge.Fridge;
import models.produce.Produce;
import models.recipes.Recipe;
import models.user.User;
import play.api.mvc.Call;
import play.db.ebean.Model;
import play.mvc.Result;

import java.util.Arrays;
import java.util.List;

import static play.libs.Json.toJson;
import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

public class FridgeController {
    public static Result fridge(final String userId) {
        final Fridge fridge = Fridge.findForUser(Long.valueOf(userId));
        return fridge != null ? ok(toJson(fridge)) : notFound("No fridge found for this user");
    }

    /**
     * Development way to add some data
     * @return
     */
    public static Result addProduce() {
        final List<Produce> produces = Arrays.asList(new Produce("butter"), new Produce("nuts"), new Produce("lentils"));
        produces.forEach(Model::save);

        return redirect(routes.FridgeController.listProduce());
    }

    public static Result listProduce() {
        return ok(toJson(new Model.Finder(Long.class, Produce.class).all()));
    }
}
