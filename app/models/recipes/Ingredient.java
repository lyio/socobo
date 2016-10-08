package models.recipes;

import com.avaje.ebean.Model;
import models.produce.Produce;

import javax.persistence.*;

@Entity
public class Ingredient extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public Produce produce;

    @ManyToOne
    public Amount amount;

    public Ingredient() {
    }

    @Override
    public String toString() {
        return amount.toString() + "of " + produce.name;
    }
}
