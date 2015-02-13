package models.fridge;

import models.produce.Produce;
import models.recipes.statics.Statics;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Resource representing the meta-data for the relationship between a {@link models.produce.Produce} and
 * the corresponding {@link models.fridge.Fridge}
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public int amount;

    @Constraints.Required
    @Column(nullable = false)
    public Statics.UNIT unit;

    @ManyToOne(cascade = CascadeType.ALL)
    public Produce produce;

    @Column(nullable = false)
    public long createdAt;

    @Column(nullable = true)
    public long lastUpdatedAt;

    @ElementCollection
    List<String> categories;
}
