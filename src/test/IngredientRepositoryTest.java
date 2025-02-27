package test;

import entity.Dish;
import entity.DishIngredient;
import entity.Ingredient;
import org.junit.jupiter.api.Test;
import repository.IngredientRepository;
import repository.Order;
import repository.Pagination;
import repository.conf.DatabaseConnection;
import test.utils.DishTestDataUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static repository.Order.OrderValue.ASC;
import static test.utils.IngredientTestDataUtils.*;

public class IngredientRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final IngredientRepository subject = new IngredientRepository(connection);

    @Test
    void find_by_id_ok(){
        Ingredient expected = saucisse();

        Ingredient actual = subject.findById(expected.getId());

        assertEquals(expected, actual);
    }


    @Test
    void read_all_ingredient_ok() {
        List<Ingredient> expecteds = List.of(
                huile(),
                oeuf(),
                pain(),
                saucisse()
        );
        Pagination pagination = new Pagination(1, 10);
        Order order = new Order("name", ASC);

        List<Ingredient> actuals = subject.findAll(pagination, order);

        assertEquals(expecteds, actuals);
    }



//    @Test
//    void read_ingredient_by_criteria_ok(){
//        List<Ingredient> expecteds = List.of(
//                saucisse()
//        );
//        Order order = new Order("name", ASC);
//        Pagination pagination = new Pagination(2, 1);
//        List<Criteria> criteria = List.of(
//                new Criteria("name", "u"),
//                new Criteria("unit", "G"),
//                new Criteria("update_datetime_from", Timestamp.valueOf("2007-06-01 00:00:00")),
//                new Criteria("update_datetime_to", Timestamp.valueOf("2007-06-20 23:59:59")),
//                new Criteria("unit_price_from", new BigDecimal("12000")),
//                new Criteria("unit_price_to", new BigDecimal("20000"))
//        );
//
//        List<Ingredient> actuals = subject.findByCriteria(criteria, order, pagination, LocalDateTime.now());
//
//        assertEquals(expecteds, actuals);
//    }

}

