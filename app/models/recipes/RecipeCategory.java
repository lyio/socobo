package models.recipes;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RecipeCategory extends Model {

    @Id
    @GeneratedValue
    private final long id;

    @Constraints.Required
    private final String name;

    public RecipeCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
