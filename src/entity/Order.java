package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static entity.StatusHistory.CREATE;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

public class Order {
    private String id;
    private String reference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ArrayList<DishOrder> dishOrders;
    private ArrayList<OrderStatus> statusHistories;

    public BigDecimal getTotalAmount(){
        return this.getDishOrders()
            .stream()
            .map(DishOrder::getCost)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    public OrderStatus getActualStatus(){
        return this.statusHistories
            .stream().max((a, b) -> {
                if (a.getCreatedAt().isAfter(b.getCreatedAt())) {
                    return 1;
                } else if (a.getCreatedAt().isBefore(b.getCreatedAt())) {
                    return -1;
                }
                return 0;
            }).orElse(new OrderStatus(randomUUID().toString(), this.getId(), CREATE, now(), now()));
    }

    public List<DishOrder> addDishOrders(List<DishOrder> dishOrders) {
        OrderStatus actualStatus = this.getActualStatus();
        if (!CREATE.equals(actualStatus.getStatus())) {
            throw new RuntimeException("Only CREATE status can be updated");
        }
        this.dishOrders.addAll(dishOrders);
        return dishOrders;
    }


    public Order(String id, String reference, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.reference = reference;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dishOrders = new ArrayList<>();
        this.statusHistories = new ArrayList<>();
    }

    public Order(String id, String reference, LocalDateTime createdAt, LocalDateTime updatedAt, ArrayList<DishOrder> dishOrders, ArrayList<OrderStatus> statusHistories) {
        this.id = id;
        this.reference = reference;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dishOrders = dishOrders;
        this.statusHistories = statusHistories;
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

    public ArrayList<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(ArrayList<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }

    public ArrayList<OrderStatus> getStatusHistories() {
        return statusHistories;
    }

    public void setStatusHistories(ArrayList<OrderStatus> statusHistories) {
        this.statusHistories = statusHistories;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(reference, order.reference) && Objects.equals(createdAt, order.createdAt) && Objects.equals(updatedAt, order.updatedAt) && Objects.equals(dishOrders, order.dishOrders) && Objects.equals(statusHistories, order.statusHistories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, createdAt, updatedAt, dishOrders, statusHistories);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", reference='" + reference + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", dishOrders=" + dishOrders +
                ", statusHistories=" + statusHistories +
                '}';
    }
}
