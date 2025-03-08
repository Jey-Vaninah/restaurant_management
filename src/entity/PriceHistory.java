package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class PriceHistory {
    private String id;
    private String idIngredient;
    private LocalDateTime priceDatetime;
    private Double unitPrice;

    public PriceHistory(String id, String idIngredient, LocalDateTime priceDatetime, Double unitPrice) {
        this.id = id;
        this.idIngredient = idIngredient;
        this.priceDatetime = priceDatetime;
        this.unitPrice = unitPrice;
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

    public LocalDateTime getPriceDatetime() {
        return priceDatetime;
    }

    public void setPriceDatetime(LocalDateTime priceDatetime) {
        this.priceDatetime = priceDatetime;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PriceHistory that = (PriceHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(idIngredient, that.idIngredient) && Objects.equals(priceDatetime, that.priceDatetime) && Objects.equals(unitPrice, that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idIngredient, priceDatetime, unitPrice);
    }

    @Override
    public String toString() {
        return "PriceHistory{" +
                "id='" + id + '\'' +
                ", idIngredient='" + idIngredient + '\'' +
                ", priceDatetime=" + priceDatetime +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
