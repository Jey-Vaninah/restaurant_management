package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static entity.IngredientStockMovementType.IN;

public class Ingredient {
    private String id;
    private String name;
    private LocalDateTime updateDatetime;
    private BigDecimal unitPrice;
    private Unit unit;
    private List<PriceHistory> priceHistories;
    private List<IngredientStockMovement> ingredientStockMovements;

    public BigDecimal getCost(LocalDateTime datetime){
        return this.priceHistories
            .stream()
            .filter(priceHistory -> priceHistory.getPriceDatetime().isBefore(datetime.plusSeconds(1)))
            .sorted(
                (a, b) -> b.getPriceDatetime().compareTo(a.getPriceDatetime())
            )
            .map(PriceHistory::getUnitPrice)
            .findFirst()
            .orElse(this.getUnitPrice());
    }

    public BigDecimal getCost(){
        return this.getCost(LocalDateTime.now());
    }

    public Float getAvailableQuantity(){
        return this.getAvailableQuantity(LocalDateTime.now());
    }

    public Float getAvailableQuantity(LocalDateTime datetime){
        return this.ingredientStockMovements
            .stream()
            .filter(
                im -> im.movementDatetime().isBefore(datetime.plusSeconds(1))
            )
            .map(im -> {
                int multiply = im.movementType().equals(IN) ? 1 : -1;
                return im.quantity() * multiply;
            })
            .reduce((float) 0, Float::sum);
    }

    public Ingredient(String id, String name, LocalDateTime updateDatetime, BigDecimal unitPrice, Unit unit, List<PriceHistory> priceHistories, List<IngredientStockMovement> ingredientStockMovements) {
        this.id = id;
        this.name = name;
        this.updateDatetime = updateDatetime;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.priceHistories = priceHistories;
        this.ingredientStockMovements = ingredientStockMovements;
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

    public LocalDateTime getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(LocalDateTime updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<PriceHistory> getPriceHistories() {
        return priceHistories;
    }

    public void setPriceHistories(List<PriceHistory> priceHistories) {
        this.priceHistories = priceHistories;
    }

    public List<IngredientStockMovement> getIngredientStocks() {
        return ingredientStockMovements;
    }

    public void setIngredientStocks(List<IngredientStockMovement> ingredientStockMovements) {
        this.ingredientStockMovements = ingredientStockMovements;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(updateDatetime, that.updateDatetime) && Objects.equals(unitPrice, that.unitPrice) && unit == that.unit && Objects.equals(priceHistories, that.priceHistories) && Objects.equals(ingredientStockMovements, that.ingredientStockMovements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, updateDatetime, unitPrice, unit, priceHistories, ingredientStockMovements);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                ", priceHistories=" + priceHistories +
                ", ingredientStockMovements=" + ingredientStockMovements +
                '}';
    }
}
