package models.recipes;

import com.avaje.ebean.Model;
import models.user.User;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public final class Recipe extends Model {

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
    public List<RecipeCategory> categories;

    @Constraints.Required
    @Column(nullable = false)
    public String pictureUrl;

    @Constraints.Required
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date creationDate;

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
