package controllers.userController;

import biz.user.UserService;
import controllers.UserController;
import models.user.User;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.status;

public class UserControllerTest_Profile extends UserControllerTestBase {

    @Before
    public void setUp() throws Exception {
        userService = mock(UserService.class);

        testUser = new User();
        testUser.name = "Test User";
        testUser.shaPassword = "password123!";
        testUser.userName = "l33t";
        testUser.authToken = "token";

        final Http.Context mockContext = getMockContext("");
        mockContext.args = new HashMap<>();
        mockContext.args.put("user", testUser);
        Http.Context.current.set(mockContext);
        controllerUnderTest = new UserController(userService);
    }

    @Test
    public void testProfile_Returns_User() throws Exception {
        final Result result = controllerUnderTest.profile(testUser.userName);

        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains(testUser.userName);
    }

    @Test
    public void testProfile_Never_Calls_UserService() throws Exception {
        controllerUnderTest.profile(testUser.userName);
        verify(userService, times(0)).profile(eq(testUser.userName));
    }

    @Test
    public void testProfile_Forbidden() throws Exception {
        final Result result = controllerUnderTest.profile("not_authorized");
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }
}
