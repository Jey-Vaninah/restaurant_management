package test.repository;

import org.junit.jupiter.api.Test;
import entity.Order;
import repository.OrderRepository;
import repository.conf.DatabaseConnection;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.utils.OrderTestDataUtils.firstOrder;

public class OrderRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final OrderRepository subject = new OrderRepository(connection);

    @Test
    void find_by_reference(){
        Order expected = firstOrder();

        Order actual = subject.findByReference(expected.getReference());

        assertEquals(expected, actual);
    }
}
