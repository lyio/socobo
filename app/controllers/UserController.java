package controllers;

import biz.UserService;
import models.user.User;
import datalayer.UserRepository;
import play.data.Form;
import play.libs.F;
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

    private final UserRepository userRepository;

    @Inject
    public UserController(final UserService userService, final UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public F.Promise<Result> createUser() {
        Form<User> userForm = Form.form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {
            return F.Promise.promise(() -> badRequest(userForm.errorsAsJson()));
        }

        final User user = userForm.get();

        return userService.createUser(user).flatMap(u -> {
            if (u != null) {
                // setting the resource location header as appropriate for REST
                response().setHeader(Http.HeaderNames.LOCATION, routes.UserController.details(u.userName).url());
                return F.Promise.promise(() -> ok(toJson(u)));
            } else {
                return F.Promise.promise(() -> internalServerError("Error creating user. Maybe username already taken"));
            }
        });
    }

    @Security.Authenticated(controllers.Authenticator.class)
    public Result details(String userId) {
        final User user = userRepository.findByUserName(userId);
        return user != null ? ok(toJson(user)) : notFound();
    }
}
