package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class PriceHistory {
    private String id;
    private String idIngredient;
    private LocalDateTime priceDatetime;
    private BigDecimal unitPrice;
//    private BigDecimal totalPrice;

    public PriceHistory(String id, String idIngredient, LocalDateTime priceDatetime, BigDecimal unitPrice) {
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

//    public BigDecimal getTotalPrice() {
//        return totalPrice;
//    }

//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceHistory that = (PriceHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(idIngredient, that.idIngredient) && Objects.equals(priceDatetime, that.priceDatetime) && Objects.equals(unitPrice, that.unitPrice) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idIngredient, priceDatetime, unitPrice);
    }

    @Override
    public String toString() {
        return "PriceHistory{" +
                "id='" + id + '\'' +
                ", id_ingredient='" + idIngredient + '\'' +
                ", priceDatetime=" + priceDatetime +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
