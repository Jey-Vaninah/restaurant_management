package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class DishOrderStatus {
    private String id;
    private String dishOrderId;
    private StatusHistory status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DishOrderStatus(String id, String dishOrderId, StatusHistory status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.dishOrderId = dishOrderId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishOrderId() {
        return dishOrderId;
    }

    public void setDishOrderId(String dishOrderId) {
        this.dishOrderId = dishOrderId;
    }

    public StatusHistory getStatus() {
        return status;
    }

    public void setStatus(StatusHistory status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DishOrderStatus that = (DishOrderStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(dishOrderId, that.dishOrderId) && status == that.status && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dishOrderId, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "DishOrderStatus{" +
                "id='" + id + '\'' +
                ", dishOrderId='" + dishOrderId + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
