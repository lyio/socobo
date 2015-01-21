package models.recipes;

import models.produce.Produce;

import javax.persistence.*;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public Produce product;

    @ManyToOne
    public Amount amount;

    public Ingredient() {
    }

    @Override
    public String toString() {
        return amount.toString() + "of " + product.name;
    }
}
