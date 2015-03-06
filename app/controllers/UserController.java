package controllers;

import biz.user.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.user.SignUp;
import models.user.User;
import play.data.Form;
import play.libs.F;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static play.libs.Json.toJson;

@Named
@Singleton
public class UserController extends Controller {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";

    private final UserService userService;

    @Inject
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    public F.Promise<Result> createUser() {
        final Form<SignUp> signUpForm = Form.form(SignUp.class).bindFromRequest();
        if (signUpForm.hasErrors()) {
            return F.Promise.promise(() -> badRequest(signUpForm.errorsAsJson()));
        }

        final SignUp newUser = signUpForm.get();

        return userService.createUser(newUser).flatMap(u -> {
            if (u != null) {
                // setting the resource location header as appropriate for REST
                response().setHeader(Http.HeaderNames.LOCATION, routes.UserController.profile(u.userName).url());
                final String authToken = userService.createTokenForUser(u);
                final ObjectNode authTokenJson = Json.newObject();
                authTokenJson.put(UserController.AUTH_TOKEN, u.authToken);
                response().setCookie(UserController.AUTH_TOKEN, u.authToken);
                return F.Promise.promise(() -> ok(toJson(u)));
            } else {
                return F.Promise.promise(() -> internalServerError("Error creating user. Maybe username already taken"));
            }
        });
    }

    @Security.Authenticated(controllers.Authenticator.class)
    public Result profile(final String userId) {
        final User user = LoginController.getUser();
        if (user.userName.equals(userId)) {
            return ok(toJson(user));
        } else {
            return forbidden();
        }
    }
}
