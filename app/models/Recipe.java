package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Recipe implements Serializable {


    private static final List<Recipe> list = new ArrayList<>(3);

    static {
        final HashMap<Long, Amount> ingr = new HashMap<>(3);
        ingr.put(1234L, new Amount(20, Amount.UNIT.PIECE));
        ingr.put(1235L, new Amount(20, Amount.UNIT.GRAM));
        ingr.put(1236L, new Amount(20, Amount.UNIT.LITRE));

        list.add(new Recipe(123L, "name", ingr, "Stir stir stir stir and then let it burn",
                new String[]{"test", "test", "test"}, CATEGORY.DESSERT));
        list.add(new Recipe(124L, "name2", ingr, "Stir stir stir stir and then let it burn",
                new String[]{"test", "test", "test"}, CATEGORY.MAIN));
        list.add(new Recipe(124L, "name3", ingr, "Stir stir stir stir and then let it burn",
                new String[]{"test", "test", "test"}, CATEGORY.SALAD));
    }

    public final long id;
    public final String name;
    public final Map<Long, Amount> ingredients;
    public final String instructions;
    public final String[] keywords;
    public final CATEGORY category;

    public Recipe(long id, String name, Map<Long, Amount> ingredients, String instructions, String[] keywords, CATEGORY category) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.keywords = keywords;
        this.category = category;
    }
//
//    public Json toJson() {
//        return new Json();
//    }


    public static List<Recipe> findAll() {
        return new ArrayList<>(list);
    }

    public static enum CATEGORY {DESSERT, MAIN, STARTER, SALAD;}


}
