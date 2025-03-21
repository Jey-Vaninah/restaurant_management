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

    private DishOrder resultSetToDishOrderStatus(ResultSet rs) throws SQLException {
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
            select * from "dish_oder" where id_order = ? order by created_at desc;
        """;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                dishOrders.add(resultSetToDishOrderStatus(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishOrders;
    }

    @Override
    public DishOrder findById(String id) {
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
    public DishOrder create(DishOrder id) {
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
