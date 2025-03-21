package repository;

import entity.Dish;
import entity.DishOrder;
import entity.DishOrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishOrderRepository implements Repository<DishOrder> {
    private final Connection connection;
    private final DishOrderStatusRepository dishOrderStatusRepository;
    private final DishRepository dishRepository;

    public DishOrderRepository(Connection connection) {
        this.connection = connection;
        this.dishOrderStatusRepository = new DishOrderStatusRepository(connection);
        this.dishRepository = new DishRepository(connection);
    }

    private DishOrder resultSetToDishOrder(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        Dish dish = dishRepository.findById(rs.getString("id_dish"));
        List<DishOrderStatus> dishOrderStatuses = dishOrderStatusRepository.findByDishOrderId(id);

        return new DishOrder(
            id,
            rs.getString("id_order"),
            dish,
            rs.getInt("quantity"),
            dishOrderStatuses
        );
    }

    public List<DishOrder> findByOrderId(String orderId) {
        List<DishOrder> dishOrders = new ArrayList<>();
        String query = """
            select * from "dish_order" where id_order = ? order by id asc;
        """;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                dishOrders.add(resultSetToDishOrder(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishOrders;
    }

    @Override
    public DishOrder create(DishOrder dishOrder) {
        String query = "insert into dish_order(id, id_order, id_dish, quantity) values (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, dishOrder.getId());
            stmt.setString(2, dishOrder.getOrderId());
            stmt.setString(3, dishOrder.getDish().getId());
            stmt.setInt(4, dishOrder.getQuantity());
            stmt.executeUpdate();
            return dishOrder;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating DishOrder: " + e.getMessage(), e);
        }
    }


    @Override
    public DishOrder findById(String id) {
        String query = "SELECT * FROM dish_order WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return resultSetToDishOrder(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding DishOrder by id: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<DishOrder> findAll(Pagination pagination, Order order) {
        return List.of();
    }

    @Override
    public DishOrder deleteById(String id) {
        return null;
    }

    @Override
    public DishOrder update(DishOrder id) {
        return null;
    }

    @Override
    public DishOrder crupdate(DishOrder id) {
        return null;
    }
}
