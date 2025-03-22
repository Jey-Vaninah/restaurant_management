package repository;

import entity.*;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static entity.StatusHistory.CREATED;

public class OrderRepository implements Repository<Order> {
    private final Connection connection;
    private final OrderStatusRepository orderStatusRepository;
    private final DishOrderRepository dishOrderRepository;

    public OrderRepository(Connection connection) {
        this.connection = connection;
        this.orderStatusRepository = new OrderStatusRepository(connection);
        this.dishOrderRepository = new DishOrderRepository(connection);
    }

    private Order resultSetToOrder(ResultSet rs) throws SQLException {
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

    public Order findByReference(String ref) {
        String query = """
            select * from "order" where reference = ?;
        """;

        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, ref);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return resultSetToOrder(rs);
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateOrderStatus(String idOrder, StatusHistory newStatus) throws SQLException {
        String query = "UPDATE order SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newStatus.name());
            stmt.setString(2, idOrder);
            stmt.executeUpdate();
        }
    }

    public void addDishToOrder(Order order, DishOrder dishOrder) throws SQLException {
        if (order.isOrderConfirmed()) {
            throw new IllegalStateException("Impossible de modifier une commande confirmée.");
        }
        order.checkIngredientsAvailable();

        dishOrderRepository.save(dishOrder);
    }

    private boolean isOrderConfirmed(String orderId) throws SQLException {
        String query = "select count(*) from order_status where id_order = ? and status = 'confirmed'";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
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
    public Order save(Order order) {
        String query = """
            insert into "order" (id ,reference, updated_at, created_at) values (?, ?, ?, ?);
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, order.getId());
            stmt.setString(2, order.getReference());
            stmt.setTimestamp(3, Timestamp.valueOf(order.getUpdatedAt()));
            stmt.setTimestamp(4, Timestamp.valueOf(order.getCreatedAt()));
            stmt.executeUpdate();

            OrderStatus createdStatus = new OrderStatus(order.getId(), "O001", CREATED, order.getUpdatedAt(), order.getUpdatedAt());
            orderStatusRepository.save(createdStatus);

            for (DishOrder dishOrder : order.getDishOrders()) {
                dishOrderRepository.save(dishOrder);
            }
            return this.findById(order.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order update(Order toUpdate) {
        String query = """
            update "order" 
                set "reference" = ?, 
                    "created_at" = ? , 
                    "updated_at" = ? ,
                where "id" = ?
        """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toUpdate.getReference());
            prs.setTimestamp(2, Timestamp.valueOf(toUpdate.getCreatedAt()));
            prs.setTimestamp(3, Timestamp.valueOf(toUpdate.getUpdatedAt()));
            prs.executeUpdate();
            return this.findById(toUpdate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Order crupdate(Order crupdateOrder) {
        final boolean isCreate = this.findById(crupdateOrder.getId()) == null;
        if (isCreate) {
            return this.save(crupdateOrder);
        }
        return this.update(crupdateOrder);
    }

    @Override
    public List<Order> saveAll(List<Order> list) {
        list.forEach(order -> {
            this.save(order);
        });
        return list;
    }
}

