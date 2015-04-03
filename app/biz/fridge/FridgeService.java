package biz.fridge;


import datalayer.FridgeRepository;
import datalayer.ItemRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import models.user.User;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

@Named
public class FridgeService {

    private final FridgeRepository fridgeRepository;

    private final ItemRepository itemRepository;

    @Inject
    public FridgeService(FridgeRepository fridgeRepository, ItemRepository itemRepository) {
        this.fridgeRepository = fridgeRepository;
        this.itemRepository = itemRepository;
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

    public Item editItem(final Long id, final String userName, Item changedItem) {
        final Item originalItem = itemRepository.findOne(changedItem.id);
        if (isItemInUsersFridge(id, userName) && originalItem != null && Objects.equals(changedItem.produce.name, originalItem.produce.name)) {
            changedItem.lastUpdatedAt = DateTime.now().getMillis();
            return itemRepository.save(changedItem);
        }
        return null;
    }

    public Item retrieveItem(Long id, String userName) {
        return isItemInUsersFridge(id, userName) ? itemRepository.findOne(id) : null;
    }

    private boolean isItemInUsersFridge(Long id, String userName) {
        return fridgeRepository.findByItemsIdAndUser_UserName(id, userName) != null;
    }
}
