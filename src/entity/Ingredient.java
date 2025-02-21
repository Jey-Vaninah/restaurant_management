package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ingredient {
    private String id;
    private String name;
    private LocalDateTime lastModified;
    private double price;
    private Unit UnitType;

    public Ingredient(String id, String name, LocalDateTime lastModified, double price, Unit unitType) {
        this.id = id;
        this.name = name;
        this.lastModified = lastModified;
        this.price = price;
        UnitType = unitType;
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

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Unit getUnitType() {
        return UnitType;
    }

    public void setUnitType(Unit unitType) {
        UnitType = unitType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id && Double.compare(price, that.price) == 0 && Objects.equals(name, that.name) && Objects.equals(lastModified, that.lastModified) && UnitType == that.UnitType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastModified, price, UnitType);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastModified=" + lastModified +
                ", price=" + price +
                ", UnitType=" + UnitType +
                '}';
    }
}
