package test.utils;

import entity.DishOrder;
import entity.DishOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

import static entity.StatusHistory.*;
import static test.utils.DishTestDataUtils.hotDog;
import static test.utils.DishTestDataUtils.omelette;

public class DishOrderTestDataUtils {
    public static DishOrder hotDogDishOrder() {
        List<DishOrderStatus> dishOrderStatus = List.of(
            new DishOrderStatus("DOS001", "DO001", CREATED, LocalDateTime.parse("2025-10-10T00:00:00"), LocalDateTime.parse("2025-10-10T00:00:00")),
            new DishOrderStatus("DOS002", "DO001", CONFIRMED, LocalDateTime.parse("2025-10-10T01:00:00"), LocalDateTime.parse("2025-10-10T01:00:00")),
            new DishOrderStatus("DOS003", "DO001", IN_PREPARATION, LocalDateTime.parse("2025-10-10T01:30:00"), LocalDateTime.parse("2025-10-10T01:30:00"))
        );

        return new DishOrder(
        "DO001",
            "O001",
            hotDog(),
            3,
            dishOrderStatus
        );
    }

    public static DishOrder omeletteDishOrder() {
        return new DishOrder(
            "DO002",
           "O001",
            omelette(),
            2,
            List.of()
        );
    }
}
