package test.entity;

import entity.Ingredient;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.utils.IngredientTestDataUtils.*;

class IngredientTest {
    private static final LocalDateTime DATETIME = LocalDateTime.parse("2025-02-24T00:00:00");

    @Test
    void saucisse_get_available_quantity(){
        Ingredient subject = saucisse();
        Float expected = 10_000f;

        Float actual = subject.getAvailableQuantity(DATETIME);

        assertEquals(expected, actual);
    }

    @Test
    void huile_get_available_quantity(){
        Ingredient subject = huile();
        Float expected = 20f;

        Float actual = subject.getAvailableQuantity(DATETIME);

        assertEquals(expected, actual);
    }

    @Test
    void oeuf_get_available_quantity(){
        Ingredient subject = oeuf();
        Float expected = 80f;

        Float actual = subject.getAvailableQuantity(DATETIME);

        assertEquals(expected, actual);
    }

    @Test
    void pain_get_available_quantity(){
        Ingredient subject = pain();
        Float expected = 30f;

        Float actual = subject.getAvailableQuantity(DATETIME);

        assertEquals(expected, actual);
    }
}
