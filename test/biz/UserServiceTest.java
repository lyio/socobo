package biz;

import biz.user.UserService;
import datalayer.FridgeRepository;
import datalayer.ProduceRepository;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    FridgeRepository fridgeRepository;

    @Mock
    ProduceRepository produceRepository;

    SignUp testUser;

    UserService serviceUnderTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testUser = new SignUp();
        testUser.userName = "test";
        testUser.password = "testPassword";

        serviceUnderTest = new UserService(userRepository);
    }

    @Test
    public void testCreateUser_Calls_Save_of_UserRepository_Once() throws Exception {
        serviceUnderTest.createUser(testUser).flatMap(u -> {
            verify(userRepository, times(1)).save(argThat(new UserMatcher()));
            return null;
        });
    }

    @Test
    public void testCreateUser_Calls_FindByUserName_Once() throws Exception {
        serviceUnderTest.createUser(testUser).flatMap(u -> {
                    verify(userRepository, times(2)).findByUserName(anyString());
                    return null;
                }
        );
    }

    @Test
    public void testCreateUser_Fails_For_Existing_UserName() throws Exception {
        serviceUnderTest.createUser(testUser);
        serviceUnderTest.createUser(testUser).flatMap(user -> {
            assertThat(user).isNull();
            return null;
        });

    }

    public class UserMatcher extends ArgumentMatcher<User> {

        @Override
        public boolean matches(Object argument) {
            boolean matches = false;
            if (argument instanceof User) {
                final User user = (User) argument;
                matches = testUser.userName.equals(user.userName);
            }
            return matches;
        }
    }
}
