package biz;

import models.recipes.statics.Statics;
import models.user.SignUp;
import models.user.User;
import datalayer.UserRepository;
import org.apache.xerces.impl.dv.util.Base64;
import org.joda.time.DateTime;
import play.libs.F;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
public class UserService {

    private static UserRepository userRepository;

    @Inject
    public UserService(final UserRepository repository) {
        userRepository = repository;
    }

    public static User findByAuthToken(String authToken) {
        return userRepository.findByAuthToken(authToken);
    }

    public F.Promise<User> createUser(final User user) {
        return F.Promise.promise(() -> {
            Optional<User> option = validateUser(user);
            if (option.isPresent()) {
                final User validatedUser = option.get();
                userRepository.save(validatedUser);
                return userRepository.findOne(validatedUser.userName);
            } else {
                throw new Exception("Error validating user");
            }
        });
    }

    public String createTokenForUser(final User u) {
        final String token = u.createToken();
        userRepository.save(u);
        return token;
    }

    /**
     * Finds user by looking for email address and password hash
     */
    public User findByEmailAddressAndPassword(String emailAddress, String password) {
        return userRepository.findByEmailAddressAndShaPassword(emailAddress, Base64.encode(User.createSha512(password)));
    }

    private Optional<User> validateUser(final User user) {
        if (userAlreadyExists(user)) {
            return Optional.empty();
        } else {
            return Optional.of(user);
        }
    }

    private boolean userAlreadyExists(final User user) {
        return userRepository.findByUserName(user.userName) != null;
    }

    public F.Promise<User> createUser(final SignUp newUser) {
        final User user = new User();
        user.userName = newUser.userName;
        user.emailAddress = newUser.email;
        user.shaPassword = Base64.encode(User.createSha512(newUser.password));
        user.createdAt = DateTime.now().getMillis();
        user.pictureUrl = Statics.DEFAULT_IMG_URL;
        return createUser(user);
    }

    public User profile(final String userName) {
        return userRepository.findByUserName(userName);
    }
}
