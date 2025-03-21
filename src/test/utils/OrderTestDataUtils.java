package test.utils;

import entity.Order;
import entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static entity.StatusHistory.*;
import static test.utils.DishOrderTestDataUtils.hotDogDishOrder;
import static test.utils.DishOrderTestDataUtils.omeletteDishOrder;

public class OrderTestDataUtils {
    public static Order firstOrder() {
        ArrayList<OrderStatus> statusHistories = new ArrayList<>(List.of(
            new OrderStatus("OS001", "O001", CREATED, LocalDateTime.parse("2025-10-10T00:00:00"), LocalDateTime.parse("2025-10-10T00:00:00")),
            new OrderStatus("OS002", "O001", CONFIRMED, LocalDateTime.parse("2025-10-10T01:00:00"), LocalDateTime.parse("2025-10-10T01:00:00")),
            new OrderStatus("OS003", "O001", IN_PREPARATION, LocalDateTime.parse("2025-10-10T01:30:00"), LocalDateTime.parse("2025-10-10T01:30:00"))
        ));

        return new Order(
            "O001",
            "R001",
            LocalDateTime.parse("2025-10-10T00:00:00"),
            LocalDateTime.parse("2025-10-10T00:00:00"),
            new ArrayList<>(List.of(hotDogDishOrder(), omeletteDishOrder())),
            statusHistories
        );
    }
}
