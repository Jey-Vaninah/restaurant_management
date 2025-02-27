package test;

import entity.Dish;
import entity.DishIngredient;
import entity.Ingredient;
import org.junit.jupiter.api.Test;
import repository.DishRepository;
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
import static test.utils.DishTestDataUtils.hotDog;
import static test.utils.IngredientTestDataUtils.*;
import static test.utils.IngredientTestDataUtils.saucisse;

public class DishReposiroryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final DishRepository subject = new DishRepository(connection);


    @Test
    void find_by_id_ok() {
        Dish expected = DishTestDataUtils.hotDog(connection);
        Dish actual = subject.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void read_all_dish_ok() {
        List<Dish> expecteds = List.of(
                DishTestDataUtils.hotDog(connection)
        );
        Pagination pagination = new Pagination(1, 10);
        Order order = new Order("name", ASC);

        List<Dish> actuals = subject.findAll(pagination, order);

        assertEquals(expecteds, actuals);
    }


    @Test
    void find_price_dish_ok() {
        Dish expected = DishTestDataUtils.hotDog(connection);
        BigDecimal expectedPrice = new BigDecimal("15000.00");

        Dish actual = subject.findById(expected.getId());

        assertEquals(expected, actual);
        assertEquals(expectedPrice, actual.getUnitPrice());
    }

    @Test
    void get_ingredient_cost_ok() {
        Dish expectedDish = DishTestDataUtils.hotDog(connection);
        BigDecimal expectedPrice = new BigDecimal("5500.00");

        Dish actualDish = subject.findById(expectedDish.getId());

        assertEquals(expectedDish.getName(), actualDish.getName(), "Les noms des plats doivent correspondre");

        assertEquals(expectedDish.getUnitPrice(), actualDish.getUnitPrice(), "Les prix des plats doivent correspondre");

        // Calcul du coût total des ingrédients pour ce plat
        BigDecimal actualPrice = BigDecimal.ZERO;
        for (Ingredient dishIngredient : actualDish.getIngredients()) {
            // Supposons que chaque `DishIngredient` a une méthode pour calculer le coût total
            actualPrice = actualPrice.add(dishIngredient.getIngredientCost());
        }

        // Vérification du coût total des ingrédients
        assertEquals(expectedPrice, actualPrice, "Le coût total des ingrédients doit correspondre à 5500.00");
    }
}

