package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderStatus {
    private String id;
    private String idOrder;
    private StatusHistory status;
    private LocalDateTime updatedAt;
    private LocalDateTime createdDate;

    public OrderStatus(String id, String idOrder, StatusHistory status, LocalDateTime updatedAt, LocalDateTime createdDate) {
        this.id = id;
        this.idOrder = idOrder;
        this.status = status;
        this.updatedAt = updatedAt;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public StatusHistory getStatus() {
        return status;
    }

    public void setStatus(StatusHistory status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(idOrder, that.idOrder) && status == that.status && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idOrder, status, updatedAt, createdDate);
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id='" + id + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", status=" + status +
                ", updatedAt=" + updatedAt +
                ", createdDate=" + createdDate +
                '}';
    }
}
