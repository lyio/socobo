package controllers;

import biz.UserService;
import models.user.User;
import datalayer.UserRepository;
import org.junit.Test;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNAUTHORIZED;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.status;

public class LoginControllerTest {

    private UserRepository userRepository;

    private UserService userService;

    private User testUser;

    private LoginController controllerUnderTest;
    private Authenticator authenticator;
    private Http.Response mockResponse;

    @org.junit.Before
    public void setUp() throws Exception {
        userRepository = mock(UserRepository.class);
        userService = mock(UserService.class);
        authenticator = mock(Authenticator.class);

        testUser = new User();
        testUser.name = "Test User";
        testUser.shaPassword = "password123!";
        testUser.userName = "l33t";

        when(authenticator.getUsername(any(Http.Context.class))).thenReturn(null);
        when(userService.createUser(any(User.class))).thenReturn(F.Promise.promise(() -> testUser));
        Http.Context.current.set(getMockContext("{\"name\": \"Thomas\", \"password\": \"password1\", \"userName\": \"lyio\"}"));
        controllerUnderTest = new LoginController(userService);
    }

    @Test
    public void testLogout_Successful() throws Exception {
        final User mockedUser = mock(User.class);
        final Http.Context mockContext = getMockContext("");
        mockContext.args = new HashMap<>();
        mockContext.args.put("user", mockedUser);
        Http.Context.current.set(mockContext);
        final Result logout = controllerUnderTest.logout();
        verify(mockResponse, atLeastOnce()).discardCookie(UserController.AUTH_TOKEN);
        verify(mockedUser, atLeastOnce()).deleteAuthToken();
        assertThat(status(logout)).isEqualTo(OK);
    }

    @Test
    public void testLogout_Unauhtorized() throws Exception{
        Result result = callAction(
                controllers.routes.ref.LoginController.logout(),
                fakeRequest()
        );
        assertThat(status(result)).isEqualTo(UNAUTHORIZED);
    }

    private Http.Context getMockContext(String body) throws Exception {
        Http.RequestBody mockBody = mock(Http.RequestBody.class);
        Http.Request mockRequest = mock(Http.Request.class);
        mockResponse = mock(Http.Response.class);
        Http.Context mockContext = mock(Http.Context.class);
        when(mockContext.request()).thenReturn(mockRequest);
        when(mockContext.response()).thenReturn(mockResponse);

        when(mockRequest.body()).thenReturn(mockBody);
        return mockContext;
    }
}
