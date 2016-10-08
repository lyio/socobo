package models.recipes;

import com.avaje.ebean.Model;
import models.recipes.statics.Statics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Amount extends Model {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    public int amount;

    @Column(nullable = false)
    public Statics.UNIT unit;

    public Amount() {
    }

    @Override
    public String toString() {
        return String.format("%s of %s", amount, unit.toString());
    }
}
