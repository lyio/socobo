package biz.fridge_service;

import biz.fridge.FridgeService;
import datalayer.FridgeRepository;
import datalayer.ItemRepository;
import datalayer.UserRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class FridgeServiceTest_GetFridge {

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

    private List<Item> testItems;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        testUser = new User();
        testUser.userName = "test user name";

        testItems = Arrays.asList(new Item(), new Item(), new Item());
        expectedFridge = new Fridge();
        expectedFridge.user = testUser;
        expectedFridge.items = testItems;

        when(fridgeRepository.findByUserUserName(eq(testUser.userName))).thenReturn(expectedFridge);

        serviceUnderTest = new FridgeService(fridgeRepository, itemRepository);
        actualResult = serviceUnderTest.getFridgeForUser(testUser.userName);
    }

    @Test
    public void testGetFridgeForUser_Returns_Fridge() throws Exception {
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(expectedFridge);
    }

    @Test
    public void testGetFridgeForUser_Calls_FridgeRepository() throws Exception {
        verify(fridgeRepository, times(1)).findByUserUserName(eq(testUser.userName));
    }

    @Test
    public void testAddItem_Calls_FridgeRepository_Once() {

    }
}
