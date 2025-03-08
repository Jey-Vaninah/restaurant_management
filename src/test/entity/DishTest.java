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
        Double expected = 5_500.00;

        Double actual = subject.getIngredientCosts();

        assertEquals(expected , actual);
    }

    @Test
    void should_calc_correct_ingredient_costs_with_specific_date(){
         expected = new BigDecimal("4000.00");

        BigDecimal actual = subject.getIngredientCosts(LocalDateTime.parse("2024-02-02T00:00:00"));

        assertEquals(expected.setScale(2, RoundingMode.HALF_UP), actual.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void should_calc_correct_gross_margin_with_current_date(){
        BigDecimal expected = new BigDecimal("9500.00");

        BigDecimal actual = subject.getGrossMargin();

        assertEquals(expected.setScale(2, RoundingMode.HALF_UP), actual.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void should_calc_correct_gross_margin_with_specific_date(){
        BigDecimal expected = new BigDecimal("11000.00");

        BigDecimal actual = subject.getGrossMargin(LocalDateTime.parse("2024-02-02T00:00:00"));

        assertEquals(expected.setScale(2, RoundingMode.HALF_UP), actual.setScale(2, RoundingMode.HALF_UP));
    }
}
