package models.recipes;

import models.recipes.statics.Statics;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Amount {

    @Id
    @GeneratedValue
    private Long id;

    public final int amount;

    public Statics.UNIT unit;

    public Amount(int amount, Statics.UNIT unit) {
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", amount, unit.toString());
    }
}
