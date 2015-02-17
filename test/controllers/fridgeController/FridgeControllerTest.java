package controllers.fridgeController;

import biz.fridge.FridgeService;
import controllers.FridgeController;
import models.fridge.Fridge;
import datalayer.FridgeRepository;
import models.fridge.Item;
import models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;
import play.mvc.Result;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class FridgeControllerTest {

    private final String testUser = "test";
    private final List<Item> fridgeItems = Arrays.asList(new Item(), new Item(), new Item());

    private FridgeRepository fridgeRepository;
    private FridgeController fridgeController;
    private Fridge expectedFridge;
    private Result fridgeResult;

    @Before
    public void setUp() throws Exception {
        expectedFridge = new Fridge(new User(testUser), fridgeItems);

        fridgeRepository = mock(FridgeRepository.class);
        when(fridgeRepository.findByUserUserName(testUser)).thenReturn(expectedFridge);

        final FridgeService fridgeService = mock(FridgeService.class);
        fridgeController = new FridgeController(
                fridgeRepository,
                 fridgeService);

        fridgeResult = fridgeController.fridge(testUser);
    }

    @Test
    public void fridge_Should_Return_Correct_Fridge() throws Exception {
        Assert.notNull(fridgeResult, "fridge should not be null");
        assertThat(status(fridgeResult)).isEqualTo(OK);
        assertThat(contentType(fridgeResult)).isEqualTo("application/json");
    }

    @Test
    public void fridge_Should_Call_Repositories() throws Exception {
        verify(fridgeRepository, times(1)).findByUserUserName(any());
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
