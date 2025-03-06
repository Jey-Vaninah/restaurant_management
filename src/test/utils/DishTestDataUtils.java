package test.utils;

import entity.Dish;
import java.math.BigDecimal;
import java.util.List;

import static test.utils.IngredientTestDataUtils.*;

public class DishTestDataUtils {
    public static Dish hotDog() {
        return new Dish(
            "D001",
            "Hot Dog",
            new BigDecimal("15000.00"),
            List.of(huile(), oeuf(), pain(), saucisse())
        );
    }
}
