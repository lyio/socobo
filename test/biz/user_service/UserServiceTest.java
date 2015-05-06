package biz.user_service;

import biz.LambdaMatcher;
import biz.fridge.FridgeService;
import biz.user.UserService;
import datalayer.UserRepository;
import models.user.SignUp;
import models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    FridgeService fridgeService;

    SignUp testUser;

    UserService serviceUnderTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testUser = new SignUp();
        testUser.userName = "test";
        testUser.password = "testPassword";

        serviceUnderTest = new UserService(userRepository, fridgeService);
    }

    @Test
    public void testCreateUser_Calls_Save_of_UserRepository_Once() throws Exception {
        serviceUnderTest.createUser(testUser).get(500);
        verify(userRepository, times(1)).save(LambdaMatcher.argThat(
                (User u) -> testUser.userName.equals(u.userName),
                "Username of saved user should be the same as the one passed in"));
    }

    @Test
    public void testCreateUser_Calls_FindByUserName_Once() throws Exception {
        final User u = serviceUnderTest.createUser(testUser).get(500);
        verify(userRepository, times(1)).findByUserName(eq(testUser.userName));
        verify(userRepository, times(1)).findOne(eq(testUser.userName));
    }

    @Test
    public void testCreateUser_Fails_For_Existing_UserName() throws Exception {
        serviceUnderTest.createUser(testUser);
        final User user = serviceUnderTest.createUser(testUser).get(500);
        assertThat(user).isNull();
    }

    @Test
    public void testCreateUser_Creates_Empty_Fridge() throws Exception {
        final User u = serviceUnderTest.createUser(testUser).get(500);
        verify(fridgeService, times(1)).createFridgeForUser(any(User.class));
    }
}
