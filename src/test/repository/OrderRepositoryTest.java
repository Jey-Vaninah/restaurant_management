package test.repository;

import entity.DishOrder;
import org.junit.jupiter.api.Test;
import entity.Order;
import repository.OrderRepository;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

import static entity.StatusHistory.CREATED;
import static org.junit.jupiter.api.Assertions.*;
import static test.utils.DishTestDataUtils.hotDog;
import static test.utils.IngredientTestDataUtils.sel;
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

    @Test
    void save_order(){
      Order order = new Order();
      DishOrder dishOrder = new DishOrder("DO003",  order.getId(), hotDog(), 1);
      order.addDishOrders(List.of(dishOrder));

      List<Order> actual = subject.saveAll(List.of(order));
      assertEquals(1, actual.size());
      Order actualOrder = actual.getFirst();
      List<DishOrder> dishOrders = actualOrder.getDishOrders();
      assertTrue(dishOrders.stream().allMatch(dio -> CREATED.equals(dio.getActualStatus().getStatus())));
    }
}
