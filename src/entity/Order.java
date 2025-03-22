package entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static entity.StatusHistory.*;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

public class Order {
    private String id;
    private String reference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ArrayList<DishOrder> dishOrders;
    private ArrayList<OrderStatus> statusHistories;

    public Order() {
        this.id = randomUUID().toString();
        this.reference = randomUUID().toString();
        this.updatedAt = now();
        this.createdAt = now();
        this.dishOrders = new ArrayList<>();
        this.statusHistories = new ArrayList<>(List.of(
            new OrderStatus(randomUUID().toString(), this.id, CREATED, now(), now())
        ));
    }

    public void confirm(){
        this.checkIngredientsAvailable();
        this.dishOrders.forEach(di -> {
            di.updateStatus(CONFIRMED);
        });

        this.statusHistories.add(new OrderStatus(randomUUID().toString(), this.id, CONFIRMED, now(), now()));
    }

    public void prepare(){
        this.dishOrders.forEach(di -> {
            di.updateStatus(IN_PREPARATION);
        });
        this.statusHistories.add(new OrderStatus(randomUUID().toString(), this.id, IN_PREPARATION, now(), now()));
    }

    public void serve(){
        boolean isAllFinished = this.dishOrders.stream().allMatch(di -> di.getActualStatus().getStatus().equals(COMPLETED));
        if(!isAllFinished){
            throw new RuntimeException("All dish orders have not been completed");
        }

        this.dishOrders.forEach(di -> {
            di.updateStatus(SERVED);
        });

        this.statusHistories.add(new OrderStatus(randomUUID().toString(), this.id, SERVED, now(), now()));
    }

    public void compeleteDishOrder(String dishOrderId) {
        DishOrder dishOrder = this.dishOrders.stream().filter(di -> di.getOrderId().equals(dishOrderId)).findFirst().orElse(null);
        if(dishOrder == null){
            throw new RuntimeException("Dish order not found");
        }

        boolean isAllFinished = this.dishOrders.stream().allMatch(di -> di.getActualStatus().getStatus().equals(COMPLETED));
        if(!isAllFinished){
            return;
        }
        this.dishOrders.forEach(di -> {
            di.updateStatus(COMPLETED);
        });
        this.statusHistories.add(new OrderStatus(randomUUID().toString(), this.id, COMPLETED, now(), now()));
    }

    public Duration getOrderDuration() {
        LocalDateTime created = this.createdAt;
        LocalDateTime servedAt = this.getStatusHistories().stream()
                .filter(status -> status.getStatus().equals(SERVED))
                .map(OrderStatus::getCreatedAt)
                .findFirst()
                .orElseThrow();

        return Duration.between(created, servedAt);
    }

    public Duration getStatusDuration(StatusHistory statusFrom, StatusHistory statusTo) {
        LocalDateTime statusFromTime = this.getStatusHistories().stream()
                .filter(status -> status.getStatus().equals(statusFrom))
                .map(OrderStatus::getCreatedAt)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status " + statusFrom + " not found"));

        LocalDateTime statusToTime = this.getStatusHistories().stream()
                .filter(status -> status.getStatus().equals(statusTo))
                .map(OrderStatus::getCreatedAt)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status " + statusTo + " not found"));

        return Duration.between(statusFromTime, statusToTime);
    }


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
            }).orElse(new OrderStatus(randomUUID().toString(), this.getId(), CREATED, now(), now()));
    }

    public List<DishOrder> addDishOrders(List<DishOrder> dishOrders) {
        OrderStatus actualStatus = this.getActualStatus();
        if (!CREATED.equals(actualStatus.getStatus())) {
            throw new RuntimeException("Only CREATE status can be updated");
        }
        this.dishOrders.addAll(dishOrders);
        return dishOrders;
    }

    public void checkIngredientsAvailable() {
        for(DishOrder dishOrder : this.getDishOrders()) {
            List<Ingredient> ingredients = dishOrder.getDish().getIngredients();
            for (Ingredient ingredient : ingredients) {
                float availableQuantity = ingredient.getAvailableQuantity(LocalDateTime.parse(ingredient.getId()));
                DishIngredient dishIngredient = dishOrder
                        .getDish()
                        .getDishIngredients()
                        .stream()
                        .filter(di -> di.getIdIngredient().equals(ingredient.getId()))
                        .findFirst()
                        .orElseThrow();

                if (availableQuantity < dishIngredient.getRequiredQuantity()) {
                    throw new RuntimeException("Ingredient " + ingredient.getId() + " is too low");
                }
            }
        }
    }

    public boolean isOrderConfirmed(){
        List<OrderStatus> orderStatuses = this.getStatusHistories().stream().filter(status -> status.getStatus().equals(CONFIRMED)).toList();
        return orderStatuses.size() > 0;
    }

    public Order(String id, String reference, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.reference = reference;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dishOrders = new ArrayList<>();
        this.statusHistories = new ArrayList<>(List.of(
            new OrderStatus(randomUUID().toString(), this.id, CREATED, now(), now())
        ));
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
