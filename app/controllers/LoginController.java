package controllers;

import biz.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.user.Login;
import models.user.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class LoginController extends Controller {

    private final UserService userService;

    @Inject
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    public Result handleLogin() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }

        Login login = loginForm.get();

        final User user = userService.findByEmailAddressAndPassword(login.emailAddress, login.password);

        if (user == null) {
            return unauthorized("email and password combination not found");
        }
        else {
            String authToken = userService.createTokenForUser(user);
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(UserController.AUTH_TOKEN, authToken);
            response().setCookie(UserController.AUTH_TOKEN, authToken);
            return ok(authTokenJson);
        }
    }

    public static User getUser() {
        return (User) Http.Context.current().args.get("user");
    }

    @Security.Authenticated(Authenticator.class)
    public Result logout() {
        response().discardCookie(UserController.AUTH_TOKEN);
        getUser().deleteAuthToken();
        return ok();
    }

}
