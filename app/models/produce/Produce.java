package models.produce;

import com.avaje.ebean.*;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represents an entity for lookup and auto suggest.
 */
@Entity
public class Produce extends Model {

    @Id
    @GeneratedValue
    Long id;

    @Constraints.Required
    public String name;

    public Produce(final String name) {
        this.name = name;
    }

    public Produce() {}
}
