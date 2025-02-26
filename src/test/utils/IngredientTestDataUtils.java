package test.utils;

import entity.Ingredient;
import entity.PriceHistory;
import entity.Unit;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static entity.Unit.G;
import static entity.Unit.U;

public class IngredientTestDataUtils {
    public static Ingredient saucisse() {
        List<PriceHistory> priceHistory = new ArrayList<>();
        priceHistory.add(new PriceHistory(
                "P001",
                "I001",
                LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                new BigDecimal("20"),
                new BigDecimal("40")
        ));

        return new Ingredient(
                "I001",
                "Saucisse",
                LocalDateTime.now(),
                new BigDecimal("20"),
                Unit.G,
                priceHistory
        );
    }


    public static Ingredient huile(){
        return new Ingredient(
                "I002",
                "Huile",
                null,
                new BigDecimal("500"),
                U
        );
    }

    public static Ingredient oeuf(){
        return new Ingredient(
                "I003",
                "Oeuf",
                null,
                new BigDecimal("500"),
                U
        );
    }

    public static Ingredient pain(){
        return new Ingredient(
                "I004",
                "pain",
                null,
                new BigDecimal("2000"),
                U
        );
    }
}