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
                new BigDecimal("20.00")
        ));

        return new Ingredient(
                "I001",
                "Saucisse",
                LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                new BigDecimal("20.00"),
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
                new BigDecimal("10000.00")
        ));

        return new Ingredient(
                "I002",
                "Huile",
                LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                new BigDecimal("10000.00"),
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
                new BigDecimal("1000.00")
        ));

        return new Ingredient(
                "I003",
                "Oeuf",
                LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                new BigDecimal("1000.00"),
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
                new BigDecimal("1000.00")
        ));

        return new Ingredient(
                "I004",
                "Pain",
                LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                new BigDecimal("1000.00"),
                U,
                priceHistory
        );
    }
}
