package models.recipes;

import models.recipes.statics.Statics;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public final class Recipe extends Model {

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

    public static List<Recipe> findAll() {
        return new Model.Finder(Long.class, Recipe.class).all();
    }
}
