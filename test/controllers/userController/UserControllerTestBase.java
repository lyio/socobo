package controllers.userController;


import biz.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import controllers.UserController;
import datalayer.UserRepository;
import models.user.User;
import play.mvc.Http;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTestBase {
    protected Http.Response mockResponse;
    protected UserRepository userRepository;
    protected UserService userService;
    protected User testUser;
    protected UserController controllerUnderTest;

    protected String requestBody;

    protected Http.Context getMockContext(String body) throws Exception {
        Http.RequestBody mockBody = mock(Http.RequestBody.class);
        Http.Request mockRequest = mock(Http.Request.class);
        mockResponse = mock(Http.Response.class);
        Http.Context mockContext = mock(Http.Context.class);
        when(mockContext.request()).thenReturn(mockRequest);
        when(mockRequest.body()).thenReturn(mockBody);
        when(mockContext.response()).thenReturn(mockResponse);
        if (body != null && !body.isEmpty()) {
            when(mockBody.asJson()).thenReturn(new ObjectMapper().readTree(body));
        }
        return mockContext;
    }
}
