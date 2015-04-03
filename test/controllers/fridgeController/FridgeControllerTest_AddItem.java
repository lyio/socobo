package controllers.fridgeController;

import biz.fridge.FridgeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ControllerTestBase;
import controllers.FridgeController;
import datalayer.FridgeRepository;
import datalayer.ProduceRepository;
import datalayer.UserRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import models.produce.Produce;
import models.recipes.statics.Statics;
import models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class FridgeControllerTest_AddItem extends ControllerTestBase {

    private final String testUser = "test";

    private final List<Item> fridgeItems = new ArrayList<>(Arrays.asList(new Item(), new Item(), new Item()));

    @Mock
    private FridgeRepository fridgeRepository;

    @Mock
    private FridgeService fridgeService;

    @Mock
    private ProduceRepository produceRepository;

    @Mock
    private UserRepository userRepository;

    private FridgeController fridgeController;

    private Result fridgeResult;

    private Item testItem;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        testItem = new Item();
        testItem.produce = new Produce("Test");
        testItem.amount = 2;
        testItem.unit = Statics.UNIT.PIECE;
        final Fridge expectedFridge = new Fridge(new User(testUser), fridgeItems);
        expectedFridge.items.add(testItem);
        when(fridgeRepository.findByUserUserName(testUser)).thenReturn(expectedFridge);
        when(fridgeService.addItem(eq(testUser), any(Item.class))).thenReturn(expectedFridge);

        final Http.Context mockContext = getMockContext(new ObjectMapper().writeValueAsString(testItem));
        when(mockContext.request().username()).thenReturn(testUser);
        mockContext.args = new HashMap<>();
        mockContext.args.put("user", new User(testUser));
        Http.Context.current.set(mockContext);

        fridgeController = new FridgeController(fridgeRepository, fridgeService);

        fridgeResult = fridgeController.addItem(testUser);
    }

    @Test
    public void testAddItem_Should_Call_FridgeService_Once() throws Exception {
        verify(fridgeService, times(1)).addItem(eq(testUser), any(Item.class));
    }

    @Test
    public void testAddItem_Should_Return_OK_And_Json() throws Exception {
        assertThat(fridgeResult).isNotNull();
        assertThat(status(fridgeResult)).isEqualTo(OK);
        assertThat(contentType(fridgeResult)).isEqualTo("application/json");
    }

    @Test
    public void testAddItem_Should_Reject_Request_For_Wrong_User() throws Exception {
        fridgeResult = fridgeController.addItem("someName");
        assertThat(status(fridgeResult)).isEqualTo(FORBIDDEN);
    }
}
