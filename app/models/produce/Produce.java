package models.produce;

import models.fridge.Fridge;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Represents an entity for lookup and auto suggest.
 */
@Entity
public class Produce extends Model{

    @Id
    public final String name;

    public Produce(final String name) {
        this.name = name;
    }

    // maybe prices and stuff like that?
    // nutritional values?

}
