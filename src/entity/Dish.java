package entity;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Dish {
    private String id;
    private String name;
    private BigDecimal unitPrice;
    private List<Ingredient> ingredients;
    private Connection connection;


    public Dish(String id, String name, BigDecimal unitPrice, List<Ingredient> ingredients, Connection connection) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.ingredients = ingredients;
        this.connection = connection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id) && Objects.equals(name, dish.name) && Objects.equals(unitPrice, dish.unitPrice) && Objects.equals(ingredients, dish.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unitPrice, ingredients);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", ingredients=" + ingredients +
                '}';
    }

    public BigDecimal getIngredientCost(String ingredientId, LocalDateTime datetime) {
        String sql = """
            SELECT iph.unit_price
            FROM ingredient_price_history iph
            WHERE iph.id_ingredient = ?
                AND iph.price_datetime <= ?
            ORDER BY iph.price_datetime DESC
            LIMIT 1;
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ingredientId);
            statement.setTimestamp(2, Timestamp.valueOf(datetime));
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("unit_price");
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getGrossMargin(LocalDateTime datetime) {
        BigDecimal totalIngredientCost = BigDecimal.ZERO;

        for (Ingredient ingredient : ingredients) {
            BigDecimal ingredientCost = getIngredientCost(ingredient.getId(), datetime);
            totalIngredientCost = totalIngredientCost.add(ingredientCost);
        }

        return unitPrice.subtract(totalIngredientCost);
    }

}
