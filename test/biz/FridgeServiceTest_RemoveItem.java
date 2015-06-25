package biz;

import biz.fridge.FridgeService;
import datalayer.FridgeRepository;
import datalayer.ItemRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.TypeSafeMatcher.*;
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
    private ArrayList<Item> expectedItems;
    private Item milch;
    private Item brot;

    private Fridge updatedFridge;

    @Before
    public void setup(){
        initMocks(this);
        serviceUnderTest = new FridgeService(fridgeRepository, itemRepository);

        fridge = new Fridge();
        milch = new Item();
        milch.id = 1L;
        brot = new Item();
        brot.id = 2L;
        fridge.items.add(milch);
        fridge.items.add(brot);
    }


    @Test
    public void removeOneItemFromFridgeTest(){
        when(fridgeRepository.findByUserUserName("Mike")).thenReturn(fridge);
        updatedFridge = serviceUnderTest.removeItem(1L, "Mike");
        assertThat(updatedFridge.items, hasItem(brot));
        assertThat(updatedFridge.items, not(hasItem(milch)));
        verify(fridgeRepository,times(1)).findByUserUserName(eq("Mike"));
        verify(fridgeRepository,times(1)).save(updatedFridge);
    }

    @Test
    public void removeTwoItemsFromFridgeTest(){
        when(fridgeRepository.findByUserUserName("Mike")).thenReturn(fridge);
        updatedFridge = serviceUnderTest.removeItem(1L, "Mike");
        updatedFridge = serviceUnderTest.removeItem(2L, "Mike");
        assertTrue(updatedFridge.items.isEmpty());
        assertTrue(updatedFridge.items.isEmpty());
        verify(fridgeRepository,times(2)).findByUserUserName(eq("Mike"));
        verify(fridgeRepository,times(2)).save(updatedFridge);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionIfItemsIsNotInFridge(){
        Fridge updatedFridge;
        final long notExistingId = 3;
        when(fridgeRepository.findByUserUserName("Mike")).thenReturn(fridge);
        updatedFridge = serviceUnderTest.removeItem(notExistingId, "Mike");
    }


}
