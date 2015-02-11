package biz;

import biz.fridge.FridgeService;
import datalayer.FridgeRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FridgeServiceTest {

    @Mock
    FridgeRepository fridgeRepository;

    @Mock
    UserRepository userRepository;

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

        serviceUnderTest = new FridgeService(fridgeRepository);
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
}
