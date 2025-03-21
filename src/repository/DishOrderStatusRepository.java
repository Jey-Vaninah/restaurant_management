package repository;

import entity.DishOrderStatus;
import entity.StatusHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishOrderStatusRepository implements Repository<DishOrderStatus> {
    private final Connection connection;

    public DishOrderStatusRepository(Connection connection) {
        this.connection = connection;
    }

    private DishOrderStatus resultSetToDishOrderStatus(ResultSet rs) throws SQLException {
        return new DishOrderStatus(
            rs.getString("id"),
            rs.getString("id_dish_order"),
            StatusHistory.valueOf(rs.getString("status")),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    public List<DishOrderStatus> findByDishOrderId(String dishOrderId) {
        List<DishOrderStatus> dishOrderStatuses = new ArrayList<>();
        String query = """
            select * from "dish_order_status" where id_dish_order = ? order by created_at desc
        """;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dishOrderId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                dishOrderStatuses.add(resultSetToDishOrderStatus(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishOrderStatuses;
    }

    @Override
    public DishOrderStatus findById(String id) {
        return null;
    }

    @Override
    public List<DishOrderStatus> findAll(Pagination pagination, Order order) {
        return List.of();
    }

    @Override
    public DishOrderStatus deleteById(String id) {
        return null;
    }

    @Override
    public DishOrderStatus create(DishOrderStatus id) {
        return null;
    }

    @Override
    public DishOrderStatus update(DishOrderStatus id) {
        return null;
    }

    @Override
    public DishOrderStatus crupdate(DishOrderStatus id) {
        return null;
    }
}
