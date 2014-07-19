package models.user;

import models.fridge.Fridge;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class User extends Model{

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne
    public final Fridge fridge;

    public User(final Fridge fridge) {
        this.fridge = fridge;
    }
}
