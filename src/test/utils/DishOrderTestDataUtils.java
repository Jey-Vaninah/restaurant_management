package test.utils;

import entity.DishOrder;
import entity.DishOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

import static entity.OrderStatus.*;
import static test.utils.DishTestDataUtils.hotDog;

public class DishOrderTestDataUtils {
    public static DishOrder hotDogDishOrder() {
        List<DishOrderStatus> dishOrderStatus = List.of(
            new DishOrderStatus("DISH-ORDER-STATUS-1", "FIRST-DISH-ORDER-ID", CREATE, LocalDateTime.parse("2025-01-01T00:00:00"), LocalDateTime.parse("2025-01-01T00:00:00")),
            new DishOrderStatus("DISH-ORDER-STATUS-1", "FIRST-DISH-ORDER-ID", IN_PREPARATION, LocalDateTime.parse("2025-01-01T01:30:00"), LocalDateTime.parse("2025-01-01T01:30:00")),
            new DishOrderStatus("DISH-ORDER-STATUS-1", "FIRST-DISH-ORDER-ID", CONFIRMED, LocalDateTime.parse("2025-01-01T01:00:00"), LocalDateTime.parse("2025-01-01T01:00:00"))
        );

        return new DishOrder(
        "FIRST-DISH-ORDER-ID",
            "DISH_ORDER_ID",
            hotDog(),
            3,
            dishOrderStatus
        );
    }
}
