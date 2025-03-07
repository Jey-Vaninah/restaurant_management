package test.utils;

import entity.Dish;
import entity.DishIngredient;
import java.math.BigDecimal;
import java.util.List;

import static entity.Unit.*;
import static test.utils.IngredientTestDataUtils.*;

public class DishTestDataUtils {
    public static Dish hotDog() {
        return new Dish(
            "D001",
            "Hot Dog",
            new BigDecimal("15000.00"),
            List.of(huile(), oeuf(), pain(), saucisse()),
            List.of(
                new DishIngredient("D001", "I001", 100f, G),
                new DishIngredient("D001", "I002", 0.15f, L),
                new DishIngredient("D001", "I003", 1f, U),
                new DishIngredient("D001", "I004", 1f, U)
            )
        );
    }
}
