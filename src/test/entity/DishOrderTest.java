package test.entity;

import entity.DishOrder;
import entity.DishOrderStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static entity.OrderStatus.IN_PREPARATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.utils.DishOrderTestDataUtils.hotDogDishOrder;

class DishOrderTest {
    private DishOrder subject = hotDogDishOrder() ;

    @Test
    void get_actual_status(){
        DishOrderStatus expected = new DishOrderStatus("DISH-ORDER-STATUS-1", "FIRST-DISH-ORDER-ID", IN_PREPARATION, LocalDateTime.parse("2025-01-01T01:30:00"), LocalDateTime.parse("2025-01-01T01:30:00"));

        DishOrderStatus actual = subject.getActualStatus();

        assertEquals(expected, actual);
    }
}
