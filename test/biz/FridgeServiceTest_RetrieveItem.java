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
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class FridgeServiceTest_RetrieveItem {

    @Mock
    FridgeRepository fridgeRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ItemRepository itemRepository;

    FridgeService serviceUnderTest;

    private User testUser;

    private Item actualResult;

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
        testItem.id = 1L;
        testItem.amount = 2;
        testItem.id = 2L;
        testItem.unit = Statics.UNIT.GRAM;

        expectedFridge = new Fridge();
        expectedFridge.user = testUser;

        when(fridgeRepository.findByItemsIdAndUser_UserName(eq(testItem.id), eq(testUser.userName))).thenReturn(expectedFridge);
        when(itemRepository.findOne(eq(testItem.id))).thenReturn(testItem);

        serviceUnderTest = new FridgeService(fridgeRepository, itemRepository);

    }

    @Test
    public void testAddItem_Calls_FridgeRepository_Once() {
        actualResult = serviceUnderTest.retrieveItem(testItem.id, testUser.userName);
        verify(fridgeRepository, times(1)).findByItemsIdAndUser_UserName(eq(testItem.id), eq(testUser.userName));
    }

    @Test
    public void testRetrieveItems_Returns_Correct_Item() {
        actualResult = serviceUnderTest.retrieveItem(testItem.id, testUser.userName);
        assertThat(actualResult.id).isEqualTo(testItem.id);
    }

    @Test
    public void testRetrieveItems_Returns_Null_When_Not_In_Users_Fridge() throws Exception {
        actualResult = serviceUnderTest.retrieveItem(testItem.id, "some other username");
        assertThat(actualResult).isNull();
    }
}
