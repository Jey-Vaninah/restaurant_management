package test.utils;

import entity.Ingredient;
import entity.PriceHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static entity.Unit.G;
import static entity.Unit.L;
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
                null,
                new BigDecimal("20"),
                G,
                priceHistory
        );
    }

    public static Ingredient huile() {
        List<PriceHistory> priceHistory = new ArrayList<>();
        priceHistory.add(new PriceHistory(
                "P002",
                "I002",
                LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                new BigDecimal("10000"),
                new BigDecimal("20000")
        ));

        return new Ingredient(
                "I002",
                "Huile",
                null,
                new BigDecimal("10000"),
                L,
                priceHistory
        );
    }

    public static Ingredient oeuf() {
        List<PriceHistory> priceHistory = new ArrayList<>();
        priceHistory.add(new PriceHistory(
                "P003",
                "I003",
                LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                new BigDecimal("1000"),
                new BigDecimal("1000")
        ));

        return new Ingredient(
                "I003",
                "Oeuf",
                null,
                new BigDecimal("1000"),
                U,
                priceHistory
        );
    }

    public static Ingredient pain() {
        List<PriceHistory> priceHistory = new ArrayList<>();
        priceHistory.add(new PriceHistory(
                "P004",
                "I004",
                LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                new BigDecimal("1000"),
                new BigDecimal("1000")
        ));

        return new Ingredient(
                "I004",
                "Pain",
                null,
                new BigDecimal("10000"),
                U,
                priceHistory
        );
    }
}
