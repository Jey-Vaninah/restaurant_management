package test.utils;

import entity.Dish;
import entity.Ingredient;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import static test.utils.IngredientTestDataUtils.*;


public class DishTestDataUtils {
    public static Dish hotDog(Connection connection) {
        return new Dish(
                "D001",
                "Hot Dog",
                new BigDecimal("15000.00"),
                List.of(huile(),saucisse(), pain(), oeuf()),
                connection
        );
    }
}
