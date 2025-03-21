package test.utils;

import entity.Order;
import entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static entity.StatusHistory.*;
import static test.utils.DishOrderTestDataUtils.hotDogDishOrder;

public class OrderTestDataUtils {
    public static Order hotDogOrder() {
        ArrayList<OrderStatus> statusHistories = new ArrayList<>(List.of(
            new OrderStatus("ORDER-STATUS-1", "FIRST-DISH-ORDER-ID", CREATE, LocalDateTime.parse("2025-01-01T00:00:00"), LocalDateTime.parse("2025-01-01T00:00:00")),
            new OrderStatus("ORDER-STATUS-2", "FIRST-DISH-ORDER-ID", IN_PREPARATION, LocalDateTime.parse("2025-01-01T01:30:00"), LocalDateTime.parse("2025-01-01T01:30:00")),
            new OrderStatus("ORDER-STATUS-3", "FIRST-DISH-ORDER-ID", CONFIRMED, LocalDateTime.parse("2025-01-01T01:00:00"), LocalDateTime.parse("2025-01-01T01:00:00"))
        ));

        return new Order(
            "FIRST-ORDER-ID",
            "FIRST-ORDER-REF",
            LocalDateTime.parse("2025-01-01T00:00:00"),
            LocalDateTime.parse("2025-01-01T00:00:00"),
            new ArrayList<>(List.of(hotDogDishOrder())),
            statusHistories
        );
    }
}
