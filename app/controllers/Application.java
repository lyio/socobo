package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.mvc.Controller;
import play.mvc.Result;

@Singleton
public class Application extends Controller {

    public Application() {
    }

    public Result home() {
        return ok("Welcome");
    }
    /**
     * Handling preflight requests for CORS
     */
    public Result preflight(String path) {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Allow", "*");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
        return ok();
    }
}
