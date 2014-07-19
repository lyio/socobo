import models.recipes.Recipe;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RecipeControllerTest {

    @Test
    public void listRecipes() {

        assertThat(Recipe.findAll()).isNotEmpty();

//        assertThat(Product.findByEan("3333333333333")).isNotNull();

//        assertThat(Product.findByEan("3333333333333").name).contains("Paperclips 3");

//        p.save();
//        assertThat(Product.findByEan("6666666666666")).isNotNull();

//        assertThat(Product.remove(p)).isTrue();

    }

    @Test
    public void newProduct() {

    }

    @Test
    public void saveProduct() {

    }

    @Test
    public void showDetails() {

    }
}
