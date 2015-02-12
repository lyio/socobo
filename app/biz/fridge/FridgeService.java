package biz.fridge;


import datalayer.FridgeRepository;
import models.fridge.Fridge;
import models.fridge.Item;

import javax.inject.Inject;
import java.util.Objects;

public class FridgeService {

    final FridgeRepository fridgeRepository;

    @Inject
    public FridgeService(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }

    public Fridge getFridgeForUser(String userName) {
        return fridgeRepository.findByUserUserName(userName);
    }

    public Fridge addItem(final String userName, final Item item) {
        final Fridge f = getFridgeForUser(userName);
        if (f.items.stream().noneMatch(i -> Objects.equals(i.id, item.id))) {
            f.items.add(item);
            fridgeRepository.save(f);
        }
        return f;
    }
}
