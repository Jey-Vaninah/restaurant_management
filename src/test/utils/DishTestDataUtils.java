package test.utils;

import entity.Dish;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import static test.utils.IngredientTestDataUtils.*;


public class DishTestDataUtils {
    public static Dish hotDog(Connection connection) {
        return new Dish(
                "D001",
                "Hot Dog",
                new BigDecimal("10000"),
                List.of(saucisse(), pain(), huile(), oeuf()),
                connection
        );
    }
}
