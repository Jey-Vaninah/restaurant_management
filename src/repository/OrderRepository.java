package repository;

import entity.*;
import entity.Order;

import java.sql.*;
import java.time.LocalDateTime;
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

    public void addDishToOrder(String orderId, DishOrder dishOrder) throws SQLException {
        if (isOrderConfirmed(orderId)) {
            throw new IllegalStateException("Impossible de modifier une commande confirmée.");
        }

        if (!areIngredientsAvailable(dishOrder)) {
            throw new IllegalStateException("Ingrédients insuffisants pour préparer ce plat.");
        }

        dishOrderRepository.create(dishOrder);
    }

    private boolean areIngredientsAvailable(DishOrder dishOrder) throws SQLException {
        List<Ingredient> ingredients = dishOrder.getDish().getIngredients();
        for (Ingredient ingredient : ingredients) {
            float availableQuantity = ingredient.getAvailableQuantity(LocalDateTime.parse(ingredient.getId()));
            DishIngredient dishIngredient = dishOrder
                .getDish()
                .getDishIngredients()
                .stream()
                .filter(di -> di.getIdIngredient().equals(ingredient.getId()))
                .findFirst()
                .orElseThrow();

            if (availableQuantity < dishIngredient.getRequiredQuantity()) {
                return false;
            }
        }
        return true;
    }

    private boolean isOrderConfirmed(String orderId) throws SQLException {
        String query = "SELECT COUNT(*) FROM order_status WHERE id_order = ? AND status = 'CONFIRMED'";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Si au moins un statut CONFIRMÉ existe
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
    public Order create(Order order) {
        String query = "INSERT INTO \"order\" (id,reference update_datetime) VALUES (?, ?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, order.getId());
            stmt.setString(1, order.getReference());
            stmt.setTimestamp(2, Timestamp.valueOf(order.getUpdatedAt()));
            stmt.executeUpdate();

            OrderStatus createdStatus = new OrderStatus(order.getId(), "O001", CREATED, order.getUpdatedAt(), order.getUpdatedAt());
            orderStatusRepository.create(createdStatus);

            for (DishOrder dishOrder : order.getDishOrders()) {
                dishOrderRepository.create(dishOrder);
            }
            return this.findById(order.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
