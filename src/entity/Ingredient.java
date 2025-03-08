package entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Ingredient {
    private String id;
    private String name;
    private LocalDateTime updateDatetime;
    private Double unitPrice;
    private Unit unit;
    private List<PriceHistory> priceHistories;

    public Ingredient(String id, String name, LocalDateTime updateDatetime, Double unitPrice, Unit unit, List<PriceHistory> priceHistories) {
        this.id = id;
        this.name = name;
        this.updateDatetime = updateDatetime;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.priceHistories = priceHistories;
    }

    public Double getCost(LocalDateTime datetime){
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

    public Double getCost(){
        return this.getCost(LocalDateTime.now());
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(updateDatetime, that.updateDatetime) && Objects.equals(unitPrice, that.unitPrice) && unit == that.unit && Objects.equals(priceHistories, that.priceHistories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, updateDatetime, unitPrice, unit, priceHistories);
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
                '}';
    }
}
