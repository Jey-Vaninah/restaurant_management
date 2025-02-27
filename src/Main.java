import entity.Dish;
import entity.DishIngredient;
import entity.Ingredient;
import repository.DishRepository;
import repository.conf.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        final Connection connection = db.getConnection();
        DishRepository DishRepository = new DishRepository(connection);
        Dish dish = DishRepository.findById("D001");
        if (dish != null) {
            BigDecimal totalIngredientCost = BigDecimal.ZERO;

            for (Ingredient dishIngredient : dish.getIngredients()) {
                BigDecimal cost = dish.getIngredientCost(DishRepository.getIngredientsByDishId(ingredient.getId.getId(), LocalDateTime.now());
                totalIngredientCost = totalIngredientCost.add(cost);
            }

            System.out.println("Coût total des ingrédients : " + totalIngredientCost);
        } else {
            System.out.println("Plat non trouvé !");
        }
        System.out.println(dish.getIngredientCost());
    }
}