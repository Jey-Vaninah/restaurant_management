package test;

import entity.Dish;
import org.junit.jupiter.api.Test;
import repository.DishRepository;
import repository.Order;
import repository.Pagination;
import repository.conf.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static repository.Order.OrderValue.ASC;
import static test.utils.DishTestDataUtils.hotDog;

public class DishReposiroryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final DishRepository subject = new DishRepository(connection);

    @Test
    void find_by_id_ok(){
        Dish expected = hotDog();

        Dish actual = subject.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void find_by_id_on_25Janvier2025_ok(){
        Dish expected = hotDog();
        LocalDateTime datetime = LocalDateTime.parse("2025-01-01");

        Dish actual = subject.findById(expected.getId(), datetime);

        assertEquals(expected, actual);
    }

    @Test
    void read_all_dish_ok() {
        List<Dish> expecteds = List.of(hotDog());

        Pagination pagination = new Pagination(1, 10);
        Order order = new Order("name", ASC);

        List<Dish> actuals = subject.findAll(pagination, order);

        assertEquals(expecteds, actuals);
    }

    @Test
    void find_price_ingredient_dish_ok() {
        Dish expected = hotDog();
        BigDecimal expectedPrice = new BigDecimal(5500);
        LocalDateTime datetime = LocalDateTime.parse("2025-01-01");

        Dish actual = subject.findById(expected.getId(), datetime);

        assertEquals(expected, actual);
        assertEquals(expectedPrice, actual.getUnitPrice());
    }
}
