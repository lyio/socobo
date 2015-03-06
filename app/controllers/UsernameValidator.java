package controllers;

import play.libs.F;
import play.mvc.*;

import javax.inject.Inject;

/**
 * Validate incoming requests to make sure only logged in users
 * can access their own resources. Uses Authenticator.class.
 */
public class UsernameValidator extends Action.Simple {

    private final Authenticator authenticator;

    @Inject
    public UsernameValidator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        final String loggedInUser = authenticator.getUsername(context);
        if (loggedInUser == null) {
            return F.Promise.promise(Results::unauthorized);
        } else if (context.request().path().contains("/" + loggedInUser + "/")) {
            return delegate.call(context);
        } else return F.Promise.promise(Results::forbidden);
    }
}
