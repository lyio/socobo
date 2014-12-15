package models.user;

import models.user.User;
import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);

    User findByUserName(String userName);
}
