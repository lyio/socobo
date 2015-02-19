package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import datalayer.FridgeRepository;
import datalayer.ProduceRepository;
import datalayer.UserRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

@Named
@Singleton
public class FridgeController extends Controller {

    private final FridgeRepository fridgeRepository;

    private final UserRepository userRepository;

    private final ProduceRepository produceRepository;

    @Inject
    public FridgeController(FridgeRepository fridgeRepository, UserRepository userRepository, ProduceRepository produceRepository) {
        this.fridgeRepository = fridgeRepository;
        this.userRepository = userRepository;
        this.produceRepository = produceRepository;
    }

    public Result showItem(String userId, String id) {
        return play.mvc.Results.TODO;
    }

    public Result fridge(final String userName) {
        final Fridge fridge = fridgeRepository.findByUserUserName(userName);
        return fridge != null ? ok(toJson(fridge)) : notFound("No fridge found for this user");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result add(final String userName) {
        final Fridge fridge = fridgeRepository.findByUserUserName(userName);
        if (fridge == null) return notFound("No fridge found for this user");
        System.out.println(request().body());
        final JsonNode node = request().body().asJson();
        if (node == null) return badRequest("received non-valid json");
        final Item item = fromJson(node, Item.class);

        fridge.items.add(item);
        fridgeRepository.save(fridge);

        return created();
    }

    /**
     * Method to remove on item from the fridge using the username and the id
     * of the item
     *
     * @param user User who want remove a item from the fridge
     * @param id Unique number of the item to identify it in the database
     * @return Result object including the updated fridge object as json
     */
    public Result removeItemFromFridge(final String user, final int id) {
        Fridge fridge = fridgeRepository.findByUserUserName(user);
        if (fridge == null) return notFound("No fridge found for this user");
        List<Item> items = fridge.items;
        items.remove(id);
        fridgeRepository.save(fridge);
        JsonNode fridgeAsJson = Json.toJson(fridge);
        return ok(fridgeAsJson).as("application/json");
    }

    public Result listProduce() {
        return ok(toJson(produceRepository.findAll()));
    }
}
