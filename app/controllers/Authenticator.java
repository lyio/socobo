package controllers;

import biz.user.UserService;
import models.user.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class Authenticator extends Security.Authenticator {

    public Authenticator() {
        super();

    }

    @Override
    public String getUsername(final Http.Context ctx) {
        User user = null;
        String[] authTokenHeaderValues = ctx.request().headers().get(UserController.AUTH_TOKEN_HEADER);
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            user = UserService.findByAuthToken(authTokenHeaderValues[0]);
            if (user != null) {
                ctx.args.put("user", user);
                return user.userName;
            }
        }

        return null;

    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return unauthorized();
    }


}
