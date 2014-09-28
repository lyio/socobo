package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.fridge.Fridge;
import models.fridge.Item;
import models.user.User;
import models.user.UserRepository;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import java.util.ArrayList;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

@Named
@Singleton
public class UserController extends Controller {

    private final UserRepository userRepository;

    @Inject
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result createUser() {
        final JsonNode node = request().body().asJson();
        final User user = fromJson(node, User.class);
        user.fridge = new Fridge(user, new ArrayList<Item>());
        userRepository.save(user);

        return ok(toJson(userRepository.findByUserName(user.userName)));
    }
}
