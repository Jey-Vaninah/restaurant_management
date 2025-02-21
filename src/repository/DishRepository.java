package repository;

import entity.Dish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DishRepository implements Repository<Dish> {
    private final Connection connection;

    public DishRepository(Connection connection) {
        this.connection = connection;
    }

    private Dish mapResultSetToDish(ResultSet rs) throws SQLException {
        return new Dish(
            rs.getString("id"),
            rs.getString("name"),
            rs.getBigDecimal("price"),
            new ArrayList<>() //TODO: get list of ingredient
        );
    }

    @Override
    public Dish findById(String id) {
        String query = "select * from \"dish\" where \"id\" = ?";
        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return mapResultSetToDish(rs);
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
