package repository;

import entity.Dish;
import entity.DishOrder;
import entity.DishOrderStatus;

import java.sql.*;
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
            while (rs.next()) {
                dishOrders.add(resultSetToDishOrder(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishOrders;
    }

    @Override
    public DishOrder save(DishOrder dishOrder) {
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
        String query = """
            SELECT dish_order.id, dish_order. FROM dish_order inner join dish on dish.id = dish_order.id WHERE id = ?";
         """;
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
    public DishOrder update(DishOrder toUpdate) {
        String query = """
            update "dishOrder" 
                set "order_id" = ?, 
                    "dish_id" = ? , 
                    "quantity" = ? ,   
                where "id" = ?
        """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toUpdate.getOrderId());
            prs.setString(2, toUpdate.getDish().getId());
            prs.setInt(3, toUpdate.getQuantity());
            prs.executeUpdate();
            return this.findById(toUpdate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public DishOrder crupdate(DishOrder crupdateDishOrder) {
        final boolean isCreate = this.findById(crupdateDishOrder.getId()) == null;
        if (isCreate) {
            return this.save(crupdateDishOrder);
        }
        return this.update(crupdateDishOrder);
    }

    @Override
    public List<DishOrder> saveAll(List<DishOrder> list) {
        return List.of();
    }
}


