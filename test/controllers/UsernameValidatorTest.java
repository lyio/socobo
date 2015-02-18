package controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.mvc.Http.Status.UNAUTHORIZED;
import static play.test.Helpers.status;


public class UsernameValidatorTest extends ControllerTestBase {

    @Mock
    Authenticator authenticator;

    private UsernameValidator usernameValidator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        usernameValidator = new UsernameValidator(authenticator);
    }

    @Test
    public void validator_Should_Call_Authenticator_getUserName_Once() throws Throwable {
        usernameValidator.call(getMockContext(null));
        verify(authenticator, times(1)).getUsername(any(Http.Context.class));
    }

    @Test
    public void validator_Should_Return_UNAUTHORIZED_when_no_one_logged_in() throws Throwable {
        Result r = usernameValidator.call(getMockContext(null)).get(500);
        assertThat(status(r)).isEqualTo(UNAUTHORIZED);
    }

    @Test
    public void validator_Should_Return_FORBIDDEN_when_wrong_user_logged_in() throws Throwable {
        when(authenticator.getUsername(any(Http.Context.class))).thenReturn("foo");
        Http.Context mockedContext = getMockContext(null);
        Http.Request request = mockedContext.request();
        when(request.path()).thenReturn("test/path/bar");
        Http.Context.current.set(mockedContext);
        Result r = usernameValidator.call(mockedContext).get(500);
        assertThat(status(r)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void validator_Should_Call_Its_Delegate() throws Throwable {
        usernameValidator.delegate = mock(Action.class);
        when(authenticator.getUsername(any(Http.Context.class))).thenReturn("foo");
        Http.Context mockedContext = getMockContext(null);
        Http.Request request = mockedContext.request();
        when(request.path()).thenReturn("test/foo/bar");
        Http.Context.current.set(mockedContext);
        usernameValidator.call(mockedContext);
        verify(usernameValidator.delegate, times(1)).call(eq(mockedContext));
    }
}
