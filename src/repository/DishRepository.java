package repository;

import entity.Dish;
import entity.DishIngredient;
import entity.Ingredient;
import entity.Unit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishRepository implements Repository<Dish> {
    private final Connection connection;

    public DishRepository(Connection connection) {
        this.connection = connection;
    }

    private Dish resultSetToDish(ResultSet rs, List<Ingredient> ingredients) throws SQLException {
        return new Dish(
                rs.getString("id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                ingredients
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
                return resultSetToDish(rs,);
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dish> findAll(Pagination pagination, Order order) {
        StringBuilder query = new StringBuilder("select * from \"dish\"");
        query.append(" order by ").append(order.getOrderBy()).append(" ").append(order.getOrderValue());
        query.append(" limit ? offset ?");

        List<Dish> dishs = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(1, pagination.getPageSize());
            preparedStatement.setInt(2, (pagination.getPage() - 1) * pagination.getPageSize());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                dishs.add(resultSetToDish(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishs;
    }

    @Override
    public Dish deleteById(String id) {
        String query = """
            delete from "dish" where "id" = ?;
        """;

        try{
            final Dish toDelete = this.findById((id));
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toDelete.getId());
            prs.executeUpdate();
            return toDelete;
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Dish create(Dish toCreate) {
        String query = """
            insert into "ingredient"("id", "name", "UnitPrice", "unit")
            values (?, ?, ?, ?);
         """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toCreate.getId());
            prs.setString (2, toCreate.getName());
            prs.setBigDecimal (3, toCreate.getUnitPrice());
            prs.setString (4, toCreate.getUnit().toString()); //LIST iingredient
            prs.executeUpdate();
            return this.findById(toCreate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Dish update(Dish toUpdate) {
        String query = """
            update "dish"
                set "name" = ? ,
                    "UnitPrice" = ?,
                    "unit" = ?
                where "id" = ?
        """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toUpdate.getId());
            prs.setString (2, toUpdate.getName());
            prs.setBigDecimal (3, toUpdate.getUnitPrice());
            prs.setString (4, toUpdate.getUnit().toString()); //LIST iingredient
            prs.executeUpdate();
            return this.findById(toUpdate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Dish crupdate(Dish crupdateDish) {
        final boolean isCreate = this.findById(crupdateDish.getId()) == null;
        if(isCreate) {
            return this.create(crupdateDish);
        }
        return this.update(crupdateDish);
    }


    public Dish getDishWithIngredients(String id) throws SQLException {
        Dish dish = null;
        List<Ingredient> ingredients = new ArrayList<>();
        String sqlDish = "SELECT * FROM dish WHERE id = ?";
        String sqlIngredients = "SELECT i.*, di.quantity, di.unit FROM dish_ingredient di " +
                "JOIN ingredient i ON di.ingredient_id = i.id WHERE di.dish_id = ?";

        if (dish != null) {
            try (PreparedStatement stmtIngredients = connection.prepareStatement(sqlIngredients)) {
                stmtIngredients.setString(1, id);
                try (ResultSet rsIngredients = stmtIngredients.executeQuery()) {
                    while (rsIngredients.next()) {
                        Ingredient ingredient = new Ingredient(
                                rsIngredients.getString("id"),
                                rsIngredients.getString("name"),
                                rsIngredients.getTimestamp("last_modified").toLocalDateTime(),
                                rsIngredients.getBigDecimal("unit_price"),
                                Unit.valueOf(rsIngredients.getString("unit"))
                        );
                        ingredients.add(ingredient);
                    }
                }
            }
        }

        try (PreparedStatement stmtDish = connection.prepareStatement(sqlDish)) {
            stmtDish.setString(1, id);
            try (ResultSet rsDish = stmtDish.executeQuery()) {
                if (rsDish.next()) {
                    dish = resultSetToDish(rsDish, ingredients);
                }
            }
        }

        return dish;
        }

    }


