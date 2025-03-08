package test.utils;

import entity.Ingredient;
import entity.IngredientStockMovement;
import entity.IngredientStockMovementType;
import entity.PriceHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static entity.IngredientStockMovementType.IN;
import static entity.IngredientStockMovementType.OUT;
import static entity.Unit.G;
import static entity.Unit.L;
import static entity.Unit.U;

public class IngredientTestDataUtils {
    public static Ingredient saucisse() {
        List<PriceHistory> priceHistories = List.of(
            new PriceHistory("P001","I001", LocalDateTime.parse("2025-01-01T00:00:00"), new BigDecimal("20.00")),
            new PriceHistory("P006","I001", LocalDateTime.parse("2024-03-03T00:00:00"), new BigDecimal("10.00")),
            new PriceHistory("P005","I001", LocalDateTime.parse("2024-02-02T00:00:00"), new BigDecimal("5.00"))
        );

        List<IngredientStockMovement> ingredientStockMovements = List.of(
            new IngredientStockMovement("S001", "I001", 10_000f, LocalDateTime.parse("2025-02-01T08:00:00"), IN , G)
        );

        return new Ingredient(
            "I001",
            "Saucisse",
            LocalDateTime.parse("2025-01-01T00:00:00"),
            new BigDecimal("20.00"),
            G,
            priceHistories,
            ingredientStockMovements
        );
    }

    public static Ingredient huile() {
        List<PriceHistory> priceHistories = List.of(
            new PriceHistory("P002","I002", LocalDateTime.parse("2025-01-01T00:00:00"), new BigDecimal("10000.00"))
        );

        List<IngredientStockMovement> ingredientStockMovements = List.of(
            new IngredientStockMovement("S002", "I002", 20f, LocalDateTime.parse("2025-02-01T08:00:00"), IN, L)
        );

        return new Ingredient(
            "I002",
            "Huile",
            LocalDateTime.parse("2025-01-01T00:00:00"),
            new BigDecimal("10000.00"),
            L,
            priceHistories,
            ingredientStockMovements
        );
    }

    public static Ingredient oeuf() {
        List<PriceHistory> priceHistories = List.of(
            new PriceHistory("P003","I003", LocalDateTime.parse("2025-01-01T00:00:00"), new BigDecimal("1000.00"))
        );

        List<IngredientStockMovement> ingredientStockMovements = List.of(
            new IngredientStockMovement("S003", "I003",100f, LocalDateTime.parse("2025-02-01T08:00:00"), IN, U),
            new IngredientStockMovement("S005", "I003",10f, LocalDateTime.parse("2025-02-02T10:00:00"), OUT, U),
            new IngredientStockMovement("S006", "I003",10f, LocalDateTime.parse("2025-02-03T15:00:00"), OUT, U)
        );

        return new Ingredient(
            "I003",
            "Oeuf",
            LocalDateTime.parse("2025-01-01T00:00:00"),
            new BigDecimal("1000.00"),
            U,
            priceHistories,
            ingredientStockMovements
        );
    }

    public static Ingredient pain() {
        List<PriceHistory> priceHistories = List.of(
            new PriceHistory("P004","I004", LocalDateTime.parse("2025-01-01T00:00:00"), new BigDecimal("1000.00"))
        );

        List<IngredientStockMovement> ingredientStockMovements = List.of(
            new IngredientStockMovement("S004", "I004", 50f, LocalDateTime.parse("2025-02-01T08:00:00"), IN, U),
            new IngredientStockMovement("S007", "I004",20f, LocalDateTime.parse("2025-02-05T16:00:00"), OUT, U)
        );

        return new Ingredient(
            "I004",
            "Pain",
            LocalDateTime.parse("2025-01-01T00:00:00"),
            new BigDecimal("1000.00"),
            U,
            priceHistories,
            ingredientStockMovements
        );
    }
}
