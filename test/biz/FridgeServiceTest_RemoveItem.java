package biz;

import biz.fridge.FridgeService;
import datalayer.FridgeRepository;
import datalayer.ItemRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.MockitoAnnotations.*;
import static org.mockito.Mockito.*;

/**
 * Created by hebner on 08.05.2015.
 */
public class FridgeServiceTest_RemoveItem {

    @Mock
    private FridgeRepository fridgeRepository;

    @Mock
    private ItemRepository itemRepository;

    private FridgeService serviceUnderTest;
    private Fridge fridge;
    @Before
    public void setup(){
        initMocks(FridgeServiceTest_RemoveItem.class);
        serviceUnderTest = new FridgeService(fridgeRepository, itemRepository);

        fridge = new Fridge();
        Item milch = new Item();
        milch.id = 1L;
        Item brot = new Item();
        brot.id = 2L;
        fridge.items.add(milch);
        fridge.items.add(brot);
    }

    @Test
    public void removeOneItemFromFridgeTest(){
        Fridge updatedFridge;
        when(fridgeRepository.findByUserUserName("Mike")).thenReturn(fridge);
        updatedFridge = serviceUnderTest.removeItem(1L, "Mike");
        assertThat(updatedFridge, not(contains("milch")));

    }

}
