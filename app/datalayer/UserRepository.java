package datalayer;

import models.user.User;
import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public interface UserRepository extends CrudRepository<User, String> {

    User findByName(String name);

    User findByUserName(String userName);

    User findByEmailAddressAndShaPassword(String email, String passwordSha);

    User findByAuthToken(String authToken);
}
