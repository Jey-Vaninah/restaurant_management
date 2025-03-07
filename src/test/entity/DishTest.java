package test.entity;

import entity.Dish;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.utils.DishTestDataUtils.hotDog;

public class DishTest {
    Dish subject = hotDog();

    @Test
    void should_calc_correct_ingredient_costs_with_current_date(){
        BigDecimal expected = new BigDecimal("5500.00");

        BigDecimal actual = subject.getIngredientCosts();

        assertEquals(expected.setScale(2, RoundingMode.DOWN), actual.setScale(2, RoundingMode.DOWN));
    }

    @Test
    void should_calc_correct_ingredient_costs_with_specific_date(){
        BigDecimal expected = new BigDecimal("4000.00");

        BigDecimal actual = subject.getIngredientCosts(LocalDateTime.parse("2024-02-02T00:00:00"));

        assertEquals(expected.setScale(2, RoundingMode.DOWN), actual.setScale(2, RoundingMode.DOWN));
    }
}
