package models.recipes;

import models.recipes.statics.Statics;
import models.user.User;
import org.joda.time.DateTime;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
public final class Recipe {

    @Id
    @GeneratedValue
    public Long id;

    @Column(length = 64, unique = false, nullable = false)
    @Constraints.Required
    public String name;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    public List<Ingredient> ingredients;

    @Constraints.MaxLength(4096)
    @Constraints.Required
    public String instructions;

    @Constraints.Required
    @ElementCollection
    public List<Statics.RECIPE_CATEGORY> categories;

    @Constraints.Required
    @Column(nullable = false)
    public String pictureUrl;

    @Constraints.Required
    @Column(length = 288, nullable = false)
    public DateTime creationDate;

    @Constraints.Required
    public int preparationTimeInMinutes;

    @Constraints.Max(5)
    @Constraints.Min(1)
    @Constraints.Required
    public int skillRequired;

    @Constraints.Required
    @ManyToOne
    public User owner;

    public Recipe() {
    }
}
