package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ingredient {
    private String id;
    private String name;
    private LocalDateTime updatedDatetime;
    private BigDecimal unitPrice;
    private Unit unit;

    public Ingredient(String id, String name, LocalDateTime updatedDatetime, BigDecimal unitPrice, Unit unit) {
        this.id = id;
        this.name = name;
        this.updatedDatetime = updatedDatetime;
        this.unitPrice = unitPrice;
        this.unit = unit;
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

    public LocalDateTime getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(LocalDateTime updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal price) {
        this.unitPrice = unitPrice;
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
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(updatedDatetime, that.updatedDatetime) && Objects.equals(price, that.price) && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, updatedDatetime, unitPrice, unit);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", updatedDatetime=" + updatedDatetime +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                '}';
    }
}
