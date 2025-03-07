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
        List<PriceHistory> priceHistories = new ArrayList<>(List.of(
            new PriceHistory("P001","I001", LocalDateTime.parse("2025-01-01T00:00:00"), new BigDecimal("20.00")),
            new PriceHistory("P006","I001", LocalDateTime.parse("2024-03-03T00:00:00"), new BigDecimal("10.00")),
            new PriceHistory("P005","I001", LocalDateTime.parse("2024-02-02T00:00:00"), new BigDecimal("5.00"))
        ));

        return new Ingredient(
            "I001",
            "Saucisse",
            LocalDateTime.parse("2025-01-01T00:00:00"),
            new BigDecimal("20.00"),
            G,
            priceHistories
        );
    }

    public static Ingredient huile() {
        List<PriceHistory> priceHistories = new ArrayList<>(List.of(
            new PriceHistory("P002","I002", LocalDateTime.parse("2025-01-01T00:00:00"), new BigDecimal("10000.00"))
        ));

        return new Ingredient(
            "I002",
            "Huile",
            LocalDateTime.parse("2025-01-01T00:00:00"),
            new BigDecimal("10000.00"),
            L,
            priceHistories
        );
    }

    public static Ingredient oeuf() {
        List<PriceHistory> priceHistories = new ArrayList<>(List.of(
            new PriceHistory("P003","I003", LocalDateTime.parse("2025-01-01T00:00:00"), new BigDecimal("1000.00"))
        ));

        return new Ingredient(
            "I003",
            "Oeuf",
            LocalDateTime.parse("2025-01-01T00:00:00"),
            new BigDecimal("1000.00"),
            U,
            priceHistories
        );
    }

    public static Ingredient pain() {
        List<PriceHistory> priceHistories = new ArrayList<>(List.of(
            new PriceHistory("P004","I004", LocalDateTime.parse("2025-01-01T00:00:00"), new BigDecimal("1000.00"))
        ));

        return new Ingredient(
            "I004",
            "Pain",
            LocalDateTime.parse("2025-01-01T00:00:00"),
            new BigDecimal("1000.00"),
            U,
            priceHistories
        );
    }
}
