package repository;

import entity.DishOrder;
import entity.Order;
import entity.OrderStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements Repository<Order> {
    private final Connection connection;
    private final OrderStatusRepository orderStatusRepository;
    private final DishOrderRepository dishOrderRepository;

    public OrderRepository(Connection connection) {
        this.connection = connection;
        this.orderStatusRepository = new OrderStatusRepository(connection);
        this.dishOrderRepository = new DishOrderRepository(connection);
    }

    private Order resultSetToOrderStatus(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        List<OrderStatus> orderStatuses = orderStatusRepository.findByOrderId(id);
        List<DishOrder> dishOrders = dishOrderRepository.findByOrderId(id);

        return new Order(
            id,
            rs.getString("reference"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime(),
            new ArrayList<>(dishOrders),
            new ArrayList<>(orderStatuses)
        );
    }

    @Override
    public Order findById(String id) {
        return null;
    }

    @Override
    public List<Order> findAll(Pagination pagination, repository.Order order) {
        return List.of();
    }

    @Override
    public Order deleteById(String id) {
        return null;
    }

    @Override
    public Order create(Order id) {
        return null;
    }

    @Override
    public Order update(Order id) {
        return null;
    }

    @Override
    public Order crupdate(Order id) {
        return null;
    }
}
