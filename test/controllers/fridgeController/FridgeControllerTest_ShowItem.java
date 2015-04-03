package controllers.fridgeController;

import biz.fridge.FridgeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ControllerTestBase;
import controllers.FridgeController;
import datalayer.FridgeRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Arrays;
import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class FridgeControllerTest_ShowItem extends ControllerTestBase {

    private final String testUser = "test";

    @Mock
    private FridgeRepository fridgeRepository;

    private FridgeController fridgeController;

    @Mock
    private FridgeService fridgeService;

    private Fridge expectedFridge;

    private Result itemResult;

    private Item testItem;

    @Before
    public void setUp() throws Throwable {
        MockitoAnnotations.initMocks(this);
        expectedFridge = new Fridge();
        expectedFridge.user = new User(testUser);
        testItem = new Item();
        testItem.id = 1L;
        expectedFridge.items = Arrays.asList(testItem);

        when(fridgeRepository.findByItemsIdAndUser_UserName(eq(testItem.id), eq(testUser))).thenReturn(expectedFridge);
        when(fridgeService.retrieveItem(eq(testItem.id), eq(testUser))).thenReturn(testItem);

        final Http.Context mockContext = getMockContext(null);
        when(mockContext.request().username()).thenReturn(testUser);
        mockContext.args = new HashMap<>();
        mockContext.args.put("user", new User(testUser));
        Http.Context.current.set(mockContext);

        fridgeController = new FridgeController(fridgeRepository, fridgeService);

        itemResult = fridgeController.showItem(testItem.id, testUser);
    }

    @Test
    public void fridge_Should_Return_Success_Status_Code() throws Exception {
        Assert.notNull(itemResult, "response should not be null");
        assertThat(status(itemResult)).isEqualTo(OK);
        assertThat(contentType(itemResult)).isEqualTo("application/json");
    }

    @Test
    public void fridge_Should_Not_Call_Repositories() throws Exception {
        verify(fridgeRepository, never()).findByUserUserName(any());
    }

    @Test
    public void fridge_Should_Call_FridgeService_Once() throws Exception {
        verify(fridgeService, times(1)).retrieveItem(eq(testItem.id), eq(testUser));
    }

    @Test
    public void fridge_Should_Return_404_When_Item_Not_In_Fridge() throws Exception {
        final Result itemResult = fridgeController.showItem(5L, "doesNotExist");
        assertThat(status(itemResult)).isEqualTo(NOT_FOUND);
    }

    @Test
    public void fridge_Should_Produce_Correct_Json() throws Exception {
        assertThat(contentAsString(itemResult)).contains("id");
        assertThat(contentAsString(itemResult)).contains("produce");
    }
}
