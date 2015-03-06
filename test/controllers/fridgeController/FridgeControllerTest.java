package controllers.fridgeController;

import biz.fridge.FridgeService;
import controllers.ControllerTestBase;
import controllers.FridgeController;
import controllers.UsernameValidator;
import datalayer.FridgeRepository;
import models.fridge.Fridge;
import models.fridge.Item;
import models.user.User;
import org.fest.util.VisibleForTesting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;
import play.libs.F;
import play.mvc.Result;
import play.mvc.Results;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class FridgeControllerTest extends ControllerTestBase {

    private final String testUser = "test";

    private final List<Item> fridgeItems = Arrays.asList(new Item(), new Item(), new Item());

    @Mock
    private FridgeRepository fridgeRepository;

    private FridgeController fridgeController;

    @Mock
    private FridgeService fridgeService;

    private Fridge expectedFridge;

    private Result fridgeResult;

    @Before
    public void setUp() throws Throwable {
        MockitoAnnotations.initMocks(this);
        expectedFridge = new Fridge(new User(testUser), fridgeItems);

        when(fridgeRepository.findByUserUserName(testUser)).thenReturn(expectedFridge);
        when(fridgeService.getFridgeForUser(eq(testUser))).thenReturn(expectedFridge);
        fridgeController = new FridgeController(fridgeRepository, fridgeService);

        fridgeResult = fridgeController.fridge(testUser);
    }

    @Test
    public void fridge_Should_Return_Correct_Fridge() throws Exception {
        Assert.notNull(fridgeResult, "fridge should not be null");
        assertThat(status(fridgeResult)).isEqualTo(OK);
        assertThat(contentType(fridgeResult)).isEqualTo("application/json");
    }

    @Test
    public void fridge_Should_Not_Call_Repositories() throws Exception {
        verify(fridgeRepository, never()).findByUserUserName(any());
    }

    @Test
    public void fridge_Should_Call_FridgeService_Once() throws Exception {
        verify(fridgeService, times(1)).getFridgeForUser(eq(testUser));
    }

    @Test
    public void fridge_Should_Return_404_When_No_Fridge_Exists() throws Exception {
        final Result fridgeResult = fridgeController.fridge("doesNotExist");
        assertThat(status(fridgeResult)).isEqualTo(NOT_FOUND);
    }

    @Test
    public void fridge_Should_Produce_Correct_Json() throws Exception {
        final Result fridgeResult = fridgeController.fridge(testUser);
        assertThat(contentAsString(fridgeResult)).contains("id");
        assertThat(contentAsString(fridgeResult)).contains("items");
    }
}
