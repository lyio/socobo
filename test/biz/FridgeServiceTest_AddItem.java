package biz;

import biz.fridge.FridgeService;
import datalayer.FridgeRepository;
import datalayer.UserRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import models.produce.Produce;
import models.recipes.statics.Statics;
import models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class FridgeServiceTest_AddItem {

    @Mock
    FridgeRepository fridgeRepository;
    @Mock
    UserRepository userRepository;

    FridgeService serviceUnderTest;

    private User testUser;

    private Fridge actualResult;

    private Fridge expectedFridge;

    private List<Item> testItems;

    private Item testItem;

    private ArrayList<Item> expectedItems;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        testUser = new User();
        testUser.userName = "test user name";

        testItem = new Item();
        testItem.amount = 2;
        testItem.id = 2L;
        testItem.unit = Statics.UNIT.GRAM;

        expectedFridge = new Fridge();
        expectedFridge.user = testUser;

        final Item specialItem = new Item();
        specialItem.id = 1L;
        final List<Item> itemList = Arrays.asList(new Item(), specialItem, new Item());
        expectedItems = new ArrayList<>(itemList);
        testItems = new ArrayList<>(itemList);

        expectedFridge.items = testItems;

        when(fridgeRepository.findByUserUserName(eq(testUser.userName))).thenReturn(expectedFridge);

        serviceUnderTest = new FridgeService(fridgeRepository);

    }

    @Test
    public void testAddItem_Calls_FridgeRepository_Once() {
        actualResult = serviceUnderTest.addItem(testUser.userName, testItem);
        verify(fridgeRepository, times(1)).save(any(Fridge.class));
    }

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
    }
}
