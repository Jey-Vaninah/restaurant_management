package test;

import entity.Ingredient;
import entity.Unit;
import org.junit.jupiter.api.Test;
import repository.IngredientRepository;
import repository.Order;
import repository.Pagination;
import repository.conf.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
                saucisse(),
                huile(),
                oeuf(),
                pain()
        );
        Pagination pagination = new Pagination(1, 10);
        Order order = new Order("name", ASC);

        List<Ingredient> actuals = subject.findAll(pagination, order);

        assertEquals(expecteds, actuals);    }
}

