package test.entity;

import entity.Dish;
import org.junit.jupiter.api.Test;
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
        Double expected = 4_000d;

        Double actual = subject.getIngredientCosts(LocalDateTime.parse("2024-02-02T00:00:00"));

        assertEquals(expected, actual);
    }

    @Test
    void should_calc_correct_gross_margin_with_current_date(){
        Double expected = 9_500d;

        Double actual = subject.getGrossMargin();

        assertEquals(expected, actual);
    }

    @Test
    void should_calc_correct_gross_margin_with_specific_date(){
        Double expected = 11_000d;

        Double actual = subject.getGrossMargin(LocalDateTime.parse("2024-02-02T00:00:00"));

        assertEquals(expected, actual);
    }
}
