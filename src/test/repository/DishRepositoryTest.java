package test.repository;

import entity.Dish;
import org.junit.jupiter.api.Test;
import repository.DishRepository;
import repository.Order;
import repository.Pagination;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static repository.Order.OrderValue.ASC;
import static test.utils.DishTestDataUtils.*;

class DishRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final DishRepository subject = new DishRepository(connection);

    Pagination pagination(){
        return new Pagination(1, 10);
    }

    Order order(){
        return new Order("name", ASC);
    }

    @Test
    void find_by_id_ok(){
        Dish expected = hotDog();

        Dish actual = subject.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void find_all_ok() {
        List<Dish> expecteds = List.of(hotDog(), omelette());

        List<Dish> actuals = subject.findAll(pagination(), order());

        assertEquals(expecteds, actuals);
    }
}

