package test.entity;

import entity.Order;
import entity.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static entity.StatusHistory.IN_PREPARATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test.utils.OrderTestDataUtils.firstOrder;

class OrderTest {
    Order subject = firstOrder();

    @Test
    void get_total_amount() {
        BigDecimal expected = new BigDecimal("65000.00");

        BigDecimal actual = subject.getTotalAmount();

        assertEquals(expected.setScale(2, RoundingMode.HALF_UP), actual.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void cannot_update_order_if_not_create() {
        RuntimeException error = assertThrows(RuntimeException.class, () -> subject.addDishOrders(List.of()));

        assertEquals("Only CREATE status can be updated", error.getMessage());
    }

    @Test
    void get_actual_status() {
        OrderStatus expected = new OrderStatus("OS003", "O001", IN_PREPARATION, LocalDateTime.parse("2025-10-10T01:30:00"), LocalDateTime.parse("2025-10-10T01:30:00"));

        OrderStatus actual = subject.getActualStatus();

        assertEquals(expected, actual);
    }
}
