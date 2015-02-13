package controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import play.mvc.Http;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTestBase {
    protected Http.Response mockResponse;

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
