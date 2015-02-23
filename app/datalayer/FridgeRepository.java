package datalayer;

import models.fridge.Fridge;
import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Provides CRUD functionality for accessing fridges and their contents. Spring Data auto-magically takes care of many standard
 * operations here.
 */
@Named
@Singleton
public interface FridgeRepository extends CrudRepository<Fridge, Long> {
    Fridge findByUserUserName(String userId);

    Fridge findByItemsIdAndUser_UserName(Long id, String userName);
}
