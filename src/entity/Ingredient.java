package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ingredient {
    private int id;
    private String name;
    private LocalDateTime lastModified;
    private double price;
    private UnitType UnitType;

    public Ingredient(int id, String name, LocalDateTime lastModified, double price, entity.UnitType unitType) {
        this.id = id;
        this.name = name;
        this.lastModified = lastModified;
        this.price = price;
        UnitType = unitType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public entity.UnitType getUnitType() {
        return UnitType;
    }

    public void setUnitType(entity.UnitType unitType) {
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
