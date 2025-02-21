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

    private Dish resultSetToDish(ResultSet rs) throws SQLException {
        return new Dish(
                rs.getString("id"),
                rs.getString("name"),
                rs.getInt("price"),
                new ArrayList<>()
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
                return resultSetToDish(rs);
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
