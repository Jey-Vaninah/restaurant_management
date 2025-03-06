import entity.Dish;
import repository.DishRepository;
import repository.conf.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        DishRepository dishRepository = new DishRepository(db.getConnection());
        Dish dish = dishRepository.findById("D001");

        System.out.println(dish);
    }
}