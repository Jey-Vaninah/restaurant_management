package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class IngredientStockMovement {
    private String id;
    private String idIngredient;
    private Float quantity;
    private LocalDateTime movementDatetime;
    private IngredientStockMovementType movementType;
    private Unit unit;

    public IngredientStockMovement(String id, String idIngredient, Float quantity, LocalDateTime movementDatetime, IngredientStockMovementType movementType, Unit unit) {
        this.id = id;
        this.idIngredient = idIngredient;
        this.quantity = quantity;
        this.movementDatetime = movementDatetime;
        this.movementType = movementType;
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getMovementDatetime() {
        return movementDatetime;
    }

    public void setMovementDatetime(LocalDateTime movementDatetime) {
        this.movementDatetime = movementDatetime;
    }

    public IngredientStockMovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(IngredientStockMovementType movementType) {
        this.movementType = movementType;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IngredientStockMovement that = (IngredientStockMovement) o;
        return Objects.equals(id, that.id) && Objects.equals(idIngredient, that.idIngredient) && Objects.equals(quantity, that.quantity) && Objects.equals(movementDatetime, that.movementDatetime) && movementType == that.movementType && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idIngredient, quantity, movementDatetime, movementType, unit);
    }

    @Override
    public String toString() {
        return "IngredientStockMovement{" +
                "id='" + id + '\'' +
                ", idIngredient='" + idIngredient + '\'' +
                ", quantity=" + quantity +
                ", movementDatetime=" + movementDatetime +
                ", movementType=" + movementType +
                ", unit=" + unit +
                '}';
    }
}
