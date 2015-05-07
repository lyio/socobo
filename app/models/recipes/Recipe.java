package models.recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.recipes.statics.Statics;
import models.user.User;
import org.joda.time.DateTime;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public final class Recipe {

    @Id
    @GeneratedValue
    public Long id;

    @Column(length = 64, unique = false, nullable = false)
    @Constraints.Required
    public String name;

    @Constraints.Required
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
    public DateTime createdAt;

    @Constraints.Required
    public int preparationTimeInMinutes;

    @Constraints.Max(5)
    @Constraints.Min(1)
    @Constraints.Required
    public int skillRequired;

    @JsonIgnore
    @Constraints.Required
    @ManyToOne
    public User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(preparationTimeInMinutes, recipe.preparationTimeInMinutes) &&
                Objects.equals(skillRequired, recipe.skillRequired) &&
                Objects.equals(id, recipe.id) &&
                Objects.equals(name, recipe.name) &&
                Objects.equals(ingredients, recipe.ingredients) &&
                Objects.equals(instructions, recipe.instructions) &&
                Objects.equals(categories, recipe.categories) &&
                Objects.equals(pictureUrl, recipe.pictureUrl) &&
                Objects.equals(createdAt, recipe.createdAt) &&
                Objects.equals(owner, recipe.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ingredients, instructions, categories, pictureUrl, createdAt, preparationTimeInMinutes, skillRequired, owner);
    }
}
