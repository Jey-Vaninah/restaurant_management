package entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static entity.StatusHistory.CREATE;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

public class DishOrder {
    private String id;
    private String orderId;
    private Dish dish;
    private int quantity;
    private List<DishOrderStatus> dishOrderStatus;

    public DishOrderStatus getActualStatus(){
        return this.dishOrderStatus
            .stream().max((a, b) -> {
                if (a.getCreatedAt().isAfter(b.getCreatedAt())) {
                    return 1;
                } else if (a.getCreatedAt().isBefore(b.getCreatedAt())) {
                    return -1;
                }
                return 0;
            }).orElse(new DishOrderStatus(randomUUID().toString(), this.getId(), CREATE, now(), now()));
    }

    public BigDecimal getCost(){
        return this.dish.getUnitPrice().multiply(new BigDecimal(this.quantity));
    }

    public DishOrder(String id, String orderId, Dish dish, int quantity, List<DishOrderStatus> dishOrderStatus) {
        this.id = id;
        this.orderId = orderId;
        this.dish = dish;
        this.quantity = quantity;
        this.dishOrderStatus = dishOrderStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<DishOrderStatus> getDishOrderStatus() {
        return dishOrderStatus;
    }

    public void setDishOrderStatus(List<DishOrderStatus> dishOrderStatus) {
        this.dishOrderStatus = dishOrderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DishOrder dishOrder = (DishOrder) o;
        return quantity == dishOrder.quantity && Objects.equals(id, dishOrder.id) && Objects.equals(orderId, dishOrder.orderId) && Objects.equals(dish, dishOrder.dish) && Objects.equals(dishOrderStatus, dishOrder.dishOrderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, dish, quantity, dishOrderStatus);
    }

    @Override
    public String toString() {
        return "DishOrder{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", dish=" + dish +
                ", quantity=" + quantity +
                ", dishOrderStatus=" + dishOrderStatus +
                '}';
    }
}
