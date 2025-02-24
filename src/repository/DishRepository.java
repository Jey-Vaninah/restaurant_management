package repository;

import entity.Dish;
import entity.Ingredient;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DishRepository implements Repository<Dish> {
    private final Connection connection;
    private final IngredientRepository ingredientRepository;

    public DishRepository(Connection connection) {
        this.connection = connection;
        this.ingredientRepository = new IngredientRepository(connection);
    }

    private Dish resultSetToDish(ResultSet rs, LocalDateTime datetime) throws SQLException {
        String dishId = rs.getString("id");
        List<Ingredient> ingredients = this.ingredientRepository.findWithSumPriceByDishId(dishId, datetime);
        BigDecimal dishPrice = ingredients
            .stream()
            .map(Ingredient::getPrice)
            .reduce(BigDecimal::add)
            .orElse(rs.getBigDecimal("price"));

        return new Dish(
            dishId,
            rs.getString("name"),
            dishPrice,
            ingredients
        );
    }

    public Dish findById(String id, LocalDateTime datetime) {
        String query = "select * from \"dish\" where \"id\" = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return resultSetToDish(rs, datetime);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish findById(String id) {
        return this.findById(id, LocalDateTime.now());
    }

    public List<Dish> findAll(Pagination pagination, Order order, LocalDateTime datetime) {
        StringBuilder query = new StringBuilder("select * from \"dish\"");
        query.append(" order by ").append(order.getOrderBy()).append(" ").append(order.getOrderValue());
        query.append(" limit ? offset ?");

        List<Dish> dishs = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(1, pagination.getPageSize());
            preparedStatement.setInt(2, (pagination.getPage() - 1) * pagination.getPageSize());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                dishs.add(resultSetToDish(rs, datetime));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishs;
    }

    @Override
    public List<Dish> findAll(Pagination pagination, Order order) {
        return this.findAll(pagination, order, LocalDateTime.now());
    }

    @Override
    public Dish deleteById(String id) {
        String query = """
            delete from "dish" where "id" = ?;
        """;

        try {
            final Dish toDelete = this.findById((id));
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString(1, toDelete.getId());
            prs.executeUpdate();
            return toDelete;
        } catch (SQLException error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public Dish create(Dish toCreate) {
        String query = """
            insert into "dish"("id", "name", "unit_price")
            values (?, ?, ?);
         """;
        try {
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString(1, toCreate.getId());
            prs.setString(2, toCreate.getName());
            prs.setBigDecimal(3, toCreate.getUnitPrice());
            prs.executeUpdate();
            return this.findById(toCreate.getId());
        } catch (SQLException error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public Dish update(Dish toUpdate) {
        String query = """
            update "dish"
                set "name" = ?,
                    "unit_price" = ?
                where "id" = ?
        """;
        try {
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString(1, toUpdate.getName());
            prs.setBigDecimal(2, toUpdate.getUnitPrice());
            prs.setString(3, toUpdate.getId());
            prs.executeUpdate();
            return this.findById(toUpdate.getId());
        } catch (SQLException error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public Dish crupdate(Dish crupdateDish) {
        final boolean isCreate = this.findById(crupdateDish.getId()) == null;
        if (isCreate) {
            return this.create(crupdateDish);
        }

        return this.update(crupdateDish);
    }
}