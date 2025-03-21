package entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {
    private String id;
    private String reference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<DishOrder> dishOrders;

    public Order(String id, String reference, LocalDateTime createdAt, LocalDateTime updatedAt, List<DishOrder> dishOrders) {
        this.id = id;
        this.reference = reference;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dishOrders = dishOrders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(reference, order.reference) && Objects.equals(createdAt, order.createdAt) && Objects.equals(updatedAt, order.updatedAt) && Objects.equals(dishOrders, order.dishOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, createdAt, updatedAt, dishOrders);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", reference='" + reference + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", dishOrders=" + dishOrders +
                '}';
    }
}
