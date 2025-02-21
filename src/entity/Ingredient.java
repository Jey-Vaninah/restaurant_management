package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ingredient {
    private String id;
    private String name;
    private LocalDateTime updatedAt;
    private BigDecimal price;
    private Unit unit;

    public Ingredient(String id, String name, LocalDateTime updatedAt, BigDecimal price, Unit unit) {
        this.id = id;
        this.name = name;
        this.updatedAt = updatedAt;
        this.price = price;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", updatedAt=" + updatedAt +
                ", price=" + price +
                ", unit=" + unit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(price, that.price) && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, updatedAt, price, unit);
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
