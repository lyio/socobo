package models.recipes;

import models.produce.Produce;
import play.db.ebean.Model;

import javax.persistence.*;

@Entity
public class Ingredient extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    public final Produce product;

    @ManyToMany(cascade = CascadeType.ALL)
    public final Amount amount;

    public Ingredient(final Produce product, final Amount amount) {
        this.product = product;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return amount.toString() + "of " + product.name;
    }
}
