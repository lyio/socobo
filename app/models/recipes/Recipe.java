package models.recipes;

import models.recipes.statics.Statics;

import javax.persistence.*;
import java.util.List;

@Entity
public final class Recipe {

    @Id
    @GeneratedValue
    public Long id;
    public final String name;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Ingredient> ingredients;

    public final String instructions;
    public final Statics.CATEGORY category;

    public Recipe(String name, List<Ingredient> ingredients, String instructions,
                  Statics.CATEGORY category) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.category = category;
    }
}
