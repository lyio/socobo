package model;

import models.produce.Produce;
import models.recipes.Amount;
import models.recipes.Ingredient;
import models.recipes.Recipe;
import models.recipes.statics.Statics;
import models.user.User;

import java.util.ArrayList;
import java.util.List;

public class TestModels {

    public static Ingredient getIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.amount = new Amount();
        ingredient.amount.amount = (int) (Math.random() * 32);
        ingredient.amount.unit = Statics.UNIT.GRAM;
        ingredient.product = new Produce(String.format("test produce %s", ingredient.amount.amount));
        return ingredient;
    }

    public static List<Ingredient> getListOfIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ingredients.add(getIngredient());
        }

        return ingredients;
    }

    public static Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.name = "Foo Bar";
        recipe.instructions = "Die Avocado halbieren, den Kern entfernen und das Fruchtfleisch aus der Schale nehmen. " +
                "Knoblauchzehen schälen und mit Avocadofruchtfleisch, Basilikum, Zitronensaft, Mandeln und Olivenöl " +
                "fein pürieren. Mit Salz und Pfeffer abschmecken.\n" +
                "\n" +
                "Butter in einer Pfanne erhitzen und die Gnocchi darin goldbraun braten. Beiseite stellen und warm halten.\n" +
                "\n" +
                "Die Kirschtomaten halbieren, etwas Butter in einer Pfanne erhitzen und die Tomatenhälften mit dem " +
                "Zucker hinzugeben. Die Hitze reduzieren, dann den Avocado-Pesto und die Gnocchi hinzufügen und gut " +
                "vermischen. Mit Salz und Pfeffer abschmecken.\n" +
                "\n" +
                "Den Parmesan reiben. Die Gnocchi auf Tellern anrichten und mit Parmesan bestreut servieren.";
        recipe.preparationTimeInMinutes = 15;
        recipe.pictureUrl = "foo.bar.example";
        recipe.ingredients = getListOfIngredients();
        recipe.skillRequired = 1;
        recipe.owner = getUser();
        return recipe;
    }

    public static User getUser() {
        User user = new User();
        user.emailAddress = "foo@socobo.com";
        user.name = "username";
        return user;
    }
}
