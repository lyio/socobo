package biz.fridge;


import datalayer.FridgeRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import models.user.User;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

@Named
public class FridgeService {

    final FridgeRepository fridgeRepository;

    @Inject
    public FridgeService(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }

    public Fridge getFridgeForUser(String userName) {
        return fridgeRepository.findByUserUserName(userName);
    }

    public void createFridgeForUser(final User user) {
        final Fridge emptyFridge = new Fridge();
        emptyFridge.user = user;
        emptyFridge.createdAt = DateTime.now().getMillis();
        fridgeRepository.save(emptyFridge);
    }

    public Fridge addItem(final String userName, final Item item) {
        final Fridge f = getFridgeForUser(userName);
        if (f.items.stream().noneMatch(i -> Objects.equals(i.id, item.id))) {
            item.createdAt = DateTime.now().getMillis();
            f.items.add(item);
            fridgeRepository.save(f);
        }
        return getFridgeForUser(userName);
    }
}
