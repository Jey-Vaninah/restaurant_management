package repository;

import entity.IngredientStockMovement;
import entity.IngredientStockMovementType;
import entity.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientStockMovementRepository implements Repository<IngredientStockMovement>{
    private final Connection connection;

    public IngredientStockMovementRepository(Connection connection) {
        this.connection = connection;
    }

    private IngredientStockMovement resultSetToIngredientStock(ResultSet rs) throws SQLException {
        return new IngredientStockMovement(
            rs.getString("id"),
            rs.getString("id_ingredient"),
            rs.getFloat("quantity"),
            rs.getTimestamp("movement_datetime").toLocalDateTime(),
            IngredientStockMovementType.valueOf(rs.getString("movement_type")),
            Unit.valueOf(rs.getString("unit"))
        );
    }

    public List<IngredientStockMovement> findByIngredientId(String idIngredient){
        List<IngredientStockMovement> ingredientStockMovements = new ArrayList<>();
        String query = """
            select *
                from "ingredient_stock_movement"
                where "id_ingredient" = ?
                order by "movement_datetime" desc
        """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idIngredient);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                ingredientStockMovements.add(resultSetToIngredientStock(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredientStockMovements;
    }

    @Override
    public IngredientStockMovement findById(String id) {
        return null;
    }

    @Override
    public List<IngredientStockMovement> findAll(Pagination pagination, Order order) {
        return List.of();
    }

    @Override
    public IngredientStockMovement deleteById(String id) {
        return null;
    }

    @Override
    public IngredientStockMovement create(IngredientStockMovement id) {
        return null;
    }

    @Override
    public IngredientStockMovement update(IngredientStockMovement id) {
        return null;
    }

    @Override
    public IngredientStockMovement crupdate(IngredientStockMovement id) {
        return null;
    }
}
