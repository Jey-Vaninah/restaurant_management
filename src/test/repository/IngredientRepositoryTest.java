package test.repository;

import entity.Ingredient;
import org.junit.jupiter.api.Test;
import repository.Criteria;
import repository.IngredientRepository;
import repository.Order;
import repository.Pagination;
import repository.conf.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static repository.Order.OrderValue.ASC;
import static test.utils.IngredientTestDataUtils.*;

class IngredientRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final IngredientRepository subject = new IngredientRepository(connection);

    Pagination pagination(){
        return new Pagination(1, 10);
    }

    Order order(){
        return new Order("name", ASC);
    }

    @Test
    void find_by_id_ok(){
        Ingredient expected = saucisse();

        Ingredient actual = subject.findById(expected.getId());

        assertEquals(expected, actual);
    }


    @Test
    void find_all_ok() {
        List<Ingredient> expecteds = List.of(
            huile(),
            oeuf(),
            pain(),
            saucisse()
        );

        List<Ingredient> actuals = subject.findAll(pagination(), order());

        assertEquals(expecteds, actuals);
    }

    @Test
    void find_by_criteria_with_name_ok() {
        List<Ingredient> expecteds = List.of(
            saucisse()
        );
        List<Criteria> criteria = List.of(new Criteria("name", "sau"));

        List<Ingredient> actuals = subject.findByCriteria(criteria, pagination(), order());

        assertEquals(expecteds, actuals);
    }

    @Test
    void find_by_criteria_with_unit_price_ok() {
        List<Ingredient> expecteds = List.of(
            huile()
        );
        List<Criteria> criteria = List.of(
            new Criteria("unit_price_from", BigDecimal.valueOf(9_000)),
            new Criteria("unit_price_to", BigDecimal.valueOf(11_000))
        );

        List<Ingredient> actuals = subject.findByCriteria(criteria, pagination(), order());

        assertEquals(expecteds, actuals);
    }

    @Test
    void find_by_criteria_empty() {
        List<Criteria> criteria = List.of(
            new Criteria("unit_price_from", BigDecimal.valueOf(5)),
            new Criteria("unit_price_to", BigDecimal.valueOf(1))
        );

        List<Ingredient> actuals = subject.findByCriteria(criteria, pagination(), order());

        assertTrue(actuals.isEmpty());
    }

    @Test
    void find_by_multiple_criteria() {
        List<Ingredient> expecteds = List.of(pain());

        List<Criteria> criteria = List.of(
            new Criteria("unit_price_from", BigDecimal.valueOf(900)),
            new Criteria("unit_price_to", BigDecimal.valueOf(1_200)),
            new Criteria("name", "Pa")
        );

        List<Ingredient> actuals = subject.findByCriteria(criteria, pagination(), order());

        assertEquals(expecteds, actuals);
    }
}