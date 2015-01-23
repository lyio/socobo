package controllers;

import biz.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.user.User;
import datalayer.UserRepository;
import org.junit.Test;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.status;

public class UserControllerTest_CreateUser {

    private UserService userService;

    private UserRepository userRepository;

    private UserController controllerUnderTest;

    private User testUser;
    private F.Promise<Result> createUserResponse;
    private String requestBody;
    private Http.Response mockResponse;

    @org.junit.Before
    public void setUp() throws Exception {
        userRepository = mock(UserRepository.class);
        userService = mock(UserService.class);

        testUser = new User();
        testUser.name = "Test User";
        testUser.password = "password123!";
        testUser.userName = "l33t";

        when(userService.createUser(any(User.class))).thenReturn(F.Promise.promise(() -> testUser));
        requestBody = "{\"pictureUrl\": \"url://example.com\", \"name\": \"Thomas\", \"password\": \"password1\", \"userName\": \"lyio\", \"email\": \"test@test.com\"}";
        Http.Context.current.set(getMockContext(requestBody));
        controllerUnderTest = new UserController(userService, userRepository);
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        Result r = controllerUnderTest.createUser().get(500);
        verify(userService, atLeastOnce()).createUser(any(User.class));
        assertThat(status(r)).isEqualTo(OK);
        verify(mockResponse, atLeastOnce()).setHeader(eq(Http.HeaderNames.LOCATION), anyString());
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
        controllerUnderTest.createUser().onRedeem(r ->
        {
            assertThat(status(r)).isNotEqualTo(OK);
        });

    }

    private Http.Context getMockContext(String body) throws Exception {
        Http.RequestBody mockBody = mock(Http.RequestBody.class);
        Http.Request mockRequest = mock(Http.Request.class);
        mockResponse = mock(Http.Response.class);
        Http.Context mockContext = mock(Http.Context.class);
        when(mockContext.request()).thenReturn(mockRequest);
        when(mockRequest.body()).thenReturn(mockBody);
        when(mockContext.response()).thenReturn(mockResponse);
        when(mockBody.asJson()).thenReturn(new ObjectMapper().readTree(body));
        return mockContext;
    }
}
