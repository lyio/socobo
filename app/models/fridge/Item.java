package models.fridge;

import models.produce.Produce;
import models.recipes.statics.Statics;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Locale;

/**
 * Resource representing the meta-data for the relationship between a {@link models.produce.Produce} and
 * the corresponding {@link models.fridge.Fridge}
 */
@Entity
public class Item {

    public final Produce produce;

    @Id
    @GeneratedValue
    public Long id;

    public final int amount;

    public final Statics.UNIT unit;

    public Item(Produce produce, int amount, Statics.UNIT unit) {
        this.produce = produce;
        this.amount = amount;
        this.unit = unit;
    }
}
