package controllers;

import biz.UserCreator;
import com.fasterxml.jackson.databind.JsonNode;
import models.user.User;
import models.user.UserRepository;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

@Named
@Singleton
public class UserController extends Controller {

    private final UserCreator userCreator;

    private final UserRepository userRepository;

    @Inject
    public UserController(final UserCreator userCreator, final UserRepository userRepository) {
        this.userCreator = userCreator;
        this.userRepository = userRepository;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result createUser() {
        final JsonNode node = request().body().asJson();
        final User user = fromJson(node, User.class);

        User createdUser = userCreator.createUser(user);


        response().setHeader(Http.HeaderNames.LOCATION, routes.UserController.details(createdUser.userName).url());
        return ok(toJson(createdUser.userName));
    }

    public Result details(String userId) {
        final User user = userRepository.findByUserName(userId);
        return user != null ? ok(toJson(user)) : notFound();
    }
}
