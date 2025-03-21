package repository;

import entity.OrderStatus;
import entity.StatusHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusRepository implements Repository<OrderStatus> {
    private final Connection connection;

    public OrderStatusRepository(Connection connection) {
        this.connection = connection;
    }

    private OrderStatus resultSetToOrderStatus(ResultSet rs) throws SQLException {
        return new OrderStatus(
            rs.getString("id"),
            rs.getString("id_order"),
            StatusHistory.valueOf(rs.getString("status")),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    public List<OrderStatus> findByOrderId(String orderId) {
        List<OrderStatus> orderStatuses = new ArrayList<>();
        String query = """
            select * from "order_status" where id_order = ? order by created_at desc
        """;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                orderStatuses.add(resultSetToOrderStatus(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderStatuses;
    }

    @Override
    public OrderStatus findById(String id) {
        return null;
    }

    @Override
    public List<OrderStatus> findAll(Pagination pagination, Order order) {
        return List.of();
    }

    @Override
    public OrderStatus deleteById(String id) {
        return null;
    }

    @Override
    public OrderStatus create(OrderStatus id) {
        return null;
    }

    @Override
    public OrderStatus update(OrderStatus id) {
        return null;
    }

    @Override
    public OrderStatus crupdate(OrderStatus id) {
        return null;
    }
}
