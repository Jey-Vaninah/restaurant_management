package repository;

import entity.DishIngredient;
import entity.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishIngredientRepository implements Repository<DishIngredient> {
    private final Connection connection;

    public DishIngredientRepository(Connection connection) {
        this.connection = connection;
    }

    DishIngredient resultSetToDishIngredient(ResultSet rs) throws SQLException {
        return new DishIngredient(
            rs.getString("id_dish"),
            rs.getString("id_ingredient"),
            rs.getFloat("required_quantity"),
            Unit.valueOf(rs.getString("unit"))
        );
    }

    public List<DishIngredient> findByDishId(String dishId){
        List<DishIngredient> dishIngredients = new ArrayList<>();

        String query = """
            select * from "dish_ingredient"
                where "dish_ingredient"."id_dish" = ?
                order by "dish_ingredient"."id_ingredient" asc;
        """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dishId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                dishIngredients.add(resultSetToDishIngredient(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishIngredients;
    }

    @Override
    public DishIngredient findById(String id) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public List<DishIngredient> findAll(Pagination pagination, Order order) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public DishIngredient deleteById(String id) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public DishIngredient create(DishIngredient id) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public DishIngredient update(DishIngredient id) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public DishIngredient crupdate(DishIngredient id) {
        throw new RuntimeException("Not Implemented");
    }
}
