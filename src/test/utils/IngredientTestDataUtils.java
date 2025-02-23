package test.utils;

import entity.Ingredient;


import java.math.BigDecimal;

import static entity.Unit.G;
import static entity.Unit.U;

public class IngredientTestDataUtils {
    public static Ingredient saucisse(){
        return new Ingredient(
                "I001",
                "Saucisse",
                null,
                new BigDecimal("12000"),
                G
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