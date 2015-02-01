package controllers;

import biz.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.UserRepository;
import models.user.User;
import org.junit.Test;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.UNAUTHORIZED;
import static play.test.Helpers.*;

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
        testUser.emailAddress = "bla@socobo.com";
        testUser.shaPassword = "password123!";
        testUser.userName = "l33t";

        when(authenticator.getUsername(any(Http.Context.class))).thenReturn(null);
        when(userService.createUser(any(User.class))).thenReturn(F.Promise.promise(() -> testUser));
        Http.Context.current.set(getMockContext("{\"password\": \"password1\", \"emailAddress\": \"lyio@socobo.com\"}"));
        controllerUnderTest = new LoginController(userService);
    }

    @Test
    public void testLogout_Successful() throws Exception {
        final User mockedUser = mock(User.class);
        final Http.Context mockContext = getMockContext(null);
        mockContext.args = new HashMap<>();
        mockContext.args.put("user", mockedUser);
        Http.Context.current.set(mockContext);
        final Result logout = controllerUnderTest.logout();
        verify(mockResponse, atLeastOnce()).discardCookie(UserController.AUTH_TOKEN);
        verify(mockedUser, atLeastOnce()).deleteAuthToken();
        assertThat(status(logout)).isEqualTo(OK);
    }

    @Test
    public void testLogout_Unauthorized() throws Exception {
        Result result = callAction(
                controllers.routes.ref.LoginController.logout(),
                fakeRequest()
        );
        assertThat(status(result)).isEqualTo(UNAUTHORIZED);
    }

    @Test
    public void testLogin_Successfull() throws Exception {
        when(userService.findByEmailAddressAndPassword(anyString(), anyString())).thenReturn(testUser);
        final Result result = controllerUnderTest.handleLogin();

        assertThat(status(result)).isEqualTo(OK);
        verify(mockResponse, atLeastOnce()).setCookie(eq(UserController.AUTH_TOKEN), anyString());
    }

    @Test
    public void testLogin_Successfull_Returns_User() throws Exception {
        when(userService.findByEmailAddressAndPassword(anyString(), anyString())).thenReturn(testUser);
        final Result result = controllerUnderTest.handleLogin();

        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentAsString(result)).contains(testUser.emailAddress);
    }

    private Http.Context getMockContext(String body) throws Exception {
        Http.RequestBody mockBody = mock(Http.RequestBody.class);
        Http.Request mockRequest = mock(Http.Request.class);
        mockResponse = mock(Http.Response.class);
        Http.Context mockContext = mock(Http.Context.class);
        when(mockContext.request()).thenReturn(mockRequest);
        when(mockRequest.body()).thenReturn(mockBody);
        when(mockContext.response()).thenReturn(mockResponse);
        if (body != null) {
            when(mockBody.asJson()).thenReturn(new ObjectMapper().readTree(body));
        }
        return mockContext;
    }
}
