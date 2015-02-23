package biz;

import biz.fridge.FridgeService;
import datalayer.FridgeRepository;
import datalayer.ItemRepository;
import datalayer.UserRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import models.recipes.statics.Statics;
import models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class FridgeServiceTest_EditItem {

    @Mock
    FridgeRepository fridgeRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ItemRepository itemRepository;

    FridgeService serviceUnderTest;

    private User testUser;

    private Fridge actualResult;

    private Fridge expectedFridge;

    private Item testItem;

    private ArrayList<Item> expectedItems;
    private Item changedItem;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        testUser = new User();
        testUser.userName = "test user name";

        testItem = new Item();
        testItem.amount = 2;
        testItem.id = 2L;
        testItem.unit = Statics.UNIT.GRAM;

        changedItem = new Item();
        changedItem.amount = 1;
        changedItem.id = 2L;
        changedItem.unit = Statics.UNIT.GRAM;

        expectedFridge = new Fridge();
        expectedFridge.user = testUser;

        expectedItems = new ArrayList<>(Arrays.asList(testItem));
        expectedFridge.items = expectedItems;

        when(fridgeRepository.findByItemsIdAndUser_UserName(eq(testItem.id), eq(testUser.userName)))
                .thenReturn(expectedFridge);
        when(fridgeRepository.findOne(anyLong())).thenReturn(expectedFridge);

        serviceUnderTest = new FridgeService(fridgeRepository, itemRepository);
    }

    @Test
    public void testEditItem_Calls_FridgeRepository_Find_Item_Once() {
        actualResult = serviceUnderTest.editItem(testItem.id, testUser.userName, changedItem);
        verify(fridgeRepository, times(1)).findByItemsIdAndUser_UserName(eq(testItem.id), eq(testUser.userName));
    }

    @Test
    public void testEditItem_Saves_New_Item() throws Exception {
        serviceUnderTest.editItem(testItem.id, testUser.userName, changedItem);
        verify(itemRepository, times(1)).save(eq(changedItem));
    }

    @Test
    public void testEditItem_Calls_FridgeRepository_Find_Fridge_Once() throws Exception {
        serviceUnderTest.editItem(testItem.id, testUser.userName, changedItem);
        verify(fridgeRepository, times(1)).findOne(anyLong());
    }

    /*
    @Test
    public void testAddItem_Returns_With_More_Items() throws Exception {
        actualResult = serviceUnderTest.addItem(testUser.userName, testItem);
        assertThat(actualResult.items.size()).isGreaterThan(expectedItems.size());
    }

    @Test
    public void testAddItem_Fails_When_Item_Already_In_Fridge() throws Exception {
        final Item item = new Item();
        item.produce = new Produce("Test");
        item.amount = 3;
        item.unit = Statics.UNIT.LITRE;
        item.id = 1L;
        actualResult = serviceUnderTest.addItem(testUser.userName, item);

        // no addition when Item already in Fridge
        assertThat(actualResult.items.size()).isEqualTo(expectedItems.size());
    }

    @Test
    public void testAddItem_Sets_CreationDate() throws Exception {
        actualResult = serviceUnderTest.addItem(testUser.userName, testItem);
        final List<Item> itemList = actualResult.items;
        final Item actualItem = itemList.get(itemList.size() - 1);
        assertThat(actualItem.createdAt).isNotEqualTo(0);
    }*/
}
