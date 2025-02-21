package repository;

import entity.Dish;
import entity.Ingredient;
import entity.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class IngredientRepository implements Repository<Ingredient> {
    final private Connection connection;

    public IngredientRepository(Connection connection) {
        this.connection = connection;
    }

    private Ingredient resultSetToIngredient (ResultSet rs) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                rs.getTimestamp("localDateTime").toLocalDateTime(),
                rs.getDouble("price"),
                Unit.valueOf(rs.getString("unit"))
        );
    }


    @Override
    public Ingredient findById(String id) {
        String query = "SELECT * FROM ingredient WHERE id = ?";
        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return resultSetToIngredient(rs);
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

