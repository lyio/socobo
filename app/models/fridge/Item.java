package models.fridge;

import models.produce.Produce;
import models.recipes.statics.Statics;

import javax.persistence.*;

/**
 * Resource representing the meta-data for the relationship between a {@link models.produce.Produce} and
 * the corresponding {@link models.fridge.Fridge}
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    public Long id;

    public int amount;

    public Statics.UNIT unit;

    @ManyToOne(cascade = CascadeType.ALL)
    public Produce produce;

    public Item() {
    }

    public Item(Produce produce, int amount, Statics.UNIT unit) {
        this.produce = produce;
        this.amount = amount;
        this.unit = unit;
    }
}
