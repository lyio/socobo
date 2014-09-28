package models.produce;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents an entity for lookup and auto suggest.
 */
@Entity
public class Produce {

    @Id
    public String name;

    public Produce(final String name) {
        this.name = name;
    }

    public Produce() {}

    // maybe prices and stuff like that?
    // nutritional values?

}
