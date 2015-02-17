package controllers.userController;

import biz.user.UserService;
import controllers.UserController;
import models.user.SignUp;
import models.user.User;
import org.junit.Test;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.status;

public class UserControllerTest_CreateUser extends UserControllerTestBase {

    private User testUser;

    @org.junit.Before
    public void setUp() throws Exception {
        userService = mock(UserService.class);

        testUser = new User();
        testUser.name = "Test User";
        testUser.shaPassword = "password123!";
        testUser.userName = "l33t";
        testUser.authToken = "token";

        when(userService.createUser(any(SignUp.class))).thenReturn(F.Promise.promise(() -> testUser));
        requestBody = "{\"pictureUrl\": \"url://example.com\", \"name\": \"Thomas\", \"password\": \"password1\", \"passwordRepeat\": \"password1\"," +
                " \"userName\": \"lyio\", \"email\": \"test@test.com\"}";
        Http.Context.current.set(getMockContext(requestBody));
        controllerUnderTest = new UserController(userService);
    }

    @Test
    public void testCreateUserSuccess_Cookie_Contains_AuthToken() throws Exception {
        Result r = controllerUnderTest.createUser().get(500);
        verify(userService, atLeastOnce()).createUser(any(SignUp.class));
        assertThat(status(r)).isEqualTo(OK);
        verify(mockResponse, atLeastOnce()).setCookie(eq(UserController.AUTH_TOKEN), eq(testUser.authToken));
    }

    @Test
    public void testCreateUserSuccess_Location_Header_Is_Set() throws Exception {
        Result r = controllerUnderTest.createUser().get(500);
        verify(userService, atLeastOnce()).createUser(any(SignUp.class));
        assertThat(status(r)).isEqualTo(OK);
        verify(mockResponse, atLeastOnce()).setHeader(eq(Http.HeaderNames.LOCATION), contains(testUser.userName));
    }

    @Test
    public void testCreateUser_Incomplete_Form() throws Exception {
        requestBody = "{\"name\": \"invalid\"}";
        Http.Context.current.set(getMockContext(requestBody));
        Result r = controllerUnderTest.createUser().get(500);
        assertThat(status(r)).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void testCreateUserFailure() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(F.Promise.throwing(new Exception()));
        controllerUnderTest.createUser().onRedeem(r -> assertThat(status(r)).isNotEqualTo(OK));
    }
}
