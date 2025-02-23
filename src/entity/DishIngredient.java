package entity;

import java.util.Objects;

public class DishIngredient {
    private String idDish;
    private String idIngredinet;
    private String requiredQuantity;
    private Unit unit;

    public DishIngredient(String idDish, String idIngredinet, String requiredQuantity, Unit unit) {
        this.idDish = idDish;
        this.idIngredinet = idIngredinet;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
    }

    public String getIdDish() {
        return idDish;
    }

    public void setIdDish(String idDish) {
        this.idDish = idDish;
    }

    public String getIdIngredinet() {
        return idIngredinet;
    }

    public void setIdIngredinet(String idIngredinet) {
        this.idIngredinet = idIngredinet;
    }

    public String getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(String requiredQuantity) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishIngredient that = (DishIngredient) o;
        return Objects.equals(idDish, that.idDish) && Objects.equals(idIngredinet, that.idIngredinet) && Objects.equals(requiredQuantity, that.requiredQuantity) && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDish, idIngredinet, requiredQuantity, unit);
    }

    @Override
    public String toString() {
        return "DishIngredient{" +
                "idDish='" + idDish + '\'' +
                ", idIngredinet='" + idIngredinet + '\'' +
                ", requiredQuantity='" + requiredQuantity + '\'' +
                ", unit=" + unit +
                '}';
    }
}

