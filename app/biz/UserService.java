package biz;

import models.fridge.Fridge;
import models.user.User;
import models.user.UserRepository;
import play.libs.F;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Optional;

@Named
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public F.Promise<User> createUser(final User user) {
        return F.Promise.promise(() -> {
            Optional<User> option = validateUser(user);
            if (option.isPresent()) {
                final User validatedUser = option.get();
                validatedUser.fridge = new Fridge(user, new ArrayList<>());
                userRepository.save(validatedUser);
                return userRepository.findByUserName(validatedUser.userName);
            } else {
                return null;
            }
        });
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
}
