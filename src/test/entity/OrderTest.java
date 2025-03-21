package test.entity;

import entity.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.utils.OrderTestDataUtils.hotDogOrder;

class OrderTest {
    Order subject = hotDogOrder();

    @Test
    void get_total_amount() {
        BigDecimal expected = new BigDecimal("45000.00");

        BigDecimal actual = subject.getTotalAmount();

        assertEquals(expected.setScale(2, RoundingMode.HALF_UP), actual.setScale(2, RoundingMode.HALF_UP));
    }
}
