package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    /**
     * Main entry point.
     * @return
     */
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
