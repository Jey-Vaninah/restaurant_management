package test.entity;

import entity.DishOrder;
import entity.DishOrderStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static entity.StatusHistory.IN_PREPARATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.utils.DishOrderTestDataUtils.hotDogDishOrder;

class DishOrderTest {
    private final DishOrder subject = hotDogDishOrder() ;

    @Test
    void get_actual_status(){
        DishOrderStatus expected = new DishOrderStatus("DOS003", "DO001", IN_PREPARATION, LocalDateTime.parse("2025-10-10T01:30:00"), LocalDateTime.parse("2025-10-10T01:30:00"));

        DishOrderStatus actual = subject.getActualStatus();

        assertEquals(expected, actual);
    }
}
