package biz;

import models.fridge.Fridge;
import models.fridge.Item;
import models.user.User;
import models.user.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;

@Named
public class UserCreator {

    private final UserRepository userRepository;

    @Inject
    public UserCreator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(final User user) {
        user.fridge = new Fridge(user, new ArrayList<Item>());

        userRepository.save(user);
        return userRepository.findByUserName(user.userName);
    }
}
