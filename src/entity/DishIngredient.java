package entity;

import java.util.Objects;

public class DishIngredient {
    private String idDish;
    private String idIngredient;
    private Float requiredQuantity;
    private Unit unit;

    public DishIngredient(String idDish, String idIngredient, Float requiredQuantity, Unit unit) {
        this.idDish = idDish;
        this.idIngredient = idIngredient;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
    }

    public String getIdDish() {
        return idDish;
    }

    public void setIdDish(String idDish) {
        this.idDish = idDish;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public Float getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Float requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
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
        DishIngredient that = (DishIngredient) o;
        return Objects.equals(idDish, that.idDish) && Objects.equals(idIngredient, that.idIngredient) && Objects.equals(requiredQuantity, that.requiredQuantity) && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDish, idIngredient, requiredQuantity, unit);
    }

    @Override
    public String toString() {
        return "DishIngredient{" +
                "idDish='" + idDish + '\'' +
                ", idIngredient='" + idIngredient + '\'' +
                ", requiredQuantity=" + requiredQuantity +
                ", unit=" + unit +
                '}';
    }
}
