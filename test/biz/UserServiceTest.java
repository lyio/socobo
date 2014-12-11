package biz;

import models.fridge.FridgeRepository;
import models.produce.ProduceRepository;
import models.user.User;
import models.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

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

    User testUser;

    UserService serviceUnderTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testUser = new User("test");
        serviceUnderTest = new UserService(userRepository);
    }

    @Test
    public void testCreateUser_Calls_UserRepository_Once() throws Exception {
        serviceUnderTest.createUser(testUser);
        verify(userRepository, times(1)).save(argThat(new UserMatcher()));
    }

    @Test
    public void testCreateUser_Calls_FindByUserName_Once() throws Exception {
        serviceUnderTest.createUser(testUser);
        verify(userRepository, times(1)).findByUserName(Matchers.eq(testUser.userName));
    }

    @Test
    public void testCreateUser_Fails_For_Existing_UserName() throws Exception {
        serviceUnderTest.createUser(testUser);
        Assert.isNull(serviceUnderTest.createUser(testUser).get(40));

    }

    private class UserMatcher extends ArgumentMatcher<User> {

        @Override
        public boolean matches(Object argument) {
            boolean matches = false;
            if (argument instanceof User) {
                final User user = (User) argument;
                matches = testUser.name.equals(user.name) && user.fridge != null;
            }
            return matches;
        }
    }
}
