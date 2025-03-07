package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Dish {
    private String id;
    private String name;
    private BigDecimal unitPrice;
    private List<Ingredient> ingredients;
    private List<DishIngredient> dishIngredients;

    public Dish(String id, String name, BigDecimal unitPrice, List<Ingredient> ingredients, List<DishIngredient> dishIngredients) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.ingredients = ingredients;
        this.dishIngredients = dishIngredients;
    }

    public BigDecimal getGrossMargin(){
        return this.getGrossMargin(LocalDateTime.now());
    }

    public BigDecimal getGrossMargin(LocalDateTime datetime){
        return this.unitPrice.subtract(this.getIngredientCosts(datetime));
    }

    public BigDecimal getIngredientCosts(){
        return this.getIngredientCosts(LocalDateTime.now());
    }

    public BigDecimal getIngredientCosts(LocalDateTime datetime){
        return this.ingredients
            .stream()
            .map(ingredient -> {
                BigDecimal unitCost = ingredient.getCost(datetime);
                DishIngredient dishIngredient = this.dishIngredients
                    .stream()
                    .filter(
                        di -> di.getIdIngredient().equals(ingredient.getId())
                    )
                    .findFirst()
                    .orElseThrow();
                return unitCost.multiply(BigDecimal.valueOf(dishIngredient.getRequiredQuantity()));
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
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

    public List<DishIngredient> getDishIngredients() {
        return dishIngredients;
    }

    public void setDishIngredients(List<DishIngredient> dishIngredients) {
        this.dishIngredients = dishIngredients;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id) && Objects.equals(name, dish.name) && Objects.equals(unitPrice, dish.unitPrice) && Objects.equals(ingredients, dish.ingredients) && Objects.equals(dishIngredients, dish.dishIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unitPrice, ingredients, dishIngredients);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", ingredients=" + ingredients +
                ", dishIngredients=" + dishIngredients +
                '}';
    }
}
