package repository;

import entity.Ingredient;
import entity.Unit;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepository implements Repository<Ingredient> {
    final private Connection connection;

    public IngredientRepository(Connection connection) {
        this.connection = connection;
    }

    private Ingredient resultSetToIngredient(ResultSet rs) throws SQLException {
        return new Ingredient(
            rs.getString("id"),
            rs.getString("name"),
            rs.getTimestamp("updated_datetime").toLocalDateTime(),
            rs.getBigDecimal("price"),
            Unit.valueOf(rs.getString("unit"))
        );
    }


    @Override
    public Ingredient findById(String id) {
        String query = "SELECT * FROM \"ingredient\" WHERE \"id\" = ?";
        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return resultSetToIngredient(rs);
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ingredient> findAll(Pagination pagination, Order order) {
        StringBuilder query = new StringBuilder("select * from \"ingredient\"");
        query.append(" order by ").append(order.getOrderBy()).append(" ").append(order.getOrderValue());
        query.append(" limit ? offset ?");

        List<Ingredient> ingredients = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(1, pagination.getPageSize());
            preparedStatement.setInt(2, (pagination.getPage() - 1) * pagination.getPageSize());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                ingredients.add(resultSetToIngredient(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    @Override
    public Ingredient deleteById(String id) {
        String query = """
            delete from "ingredient" where "id" = ?;
        """;

        try{
            final Ingredient toDelete = this.findById((id));
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toDelete.getId());
            prs.executeUpdate();
            return toDelete;
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Ingredient create(Ingredient toCreate) {
        String query = """
            insert into "ingredient"("id", "name", "gender", "birth_date")
            values (?, ?, ?, ?, ?);
         """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toCreate.getId());
            prs.setString (2, toCreate.getName());
            prs.setTimestamp(3, Timestamp.valueOf(toCreate.getUpdatedDatetime()));
            prs.setBigDecimal (4, toCreate.getPrice());
            prs.setString (5, toCreate.getUnit().toString());
            prs.executeUpdate();
            return this.findById(toCreate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Ingredient update(Ingredient toUpdate) {
        String query = """
            update "ingredient"
                set "name" = ? ,
                    "updated_datetime" = ?,
                    "price" = ?,
                    "unit" = ?
                where "id" = ?
        """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toUpdate.getId());
            prs.setString (2, toUpdate.getName());
            prs.setTimestamp(3, Timestamp.valueOf(toUpdate.getUpdatedDatetime()));
            prs.setBigDecimal(4, toUpdate.getPrice());
            prs.setString (5, toUpdate.getUnit().toString());
            prs.executeUpdate();
            return this.findById(toUpdate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Ingredient crupdate(Ingredient crupdateIngredient) {
        final boolean isCreate = this.findById(crupdateIngredient.getId()) == null;
        if(isCreate) {
            return this.create(crupdateIngredient);
        }
        return this.update(crupdateIngredient);
    }

    public BigDecimal getLatestPriceOrDefault(String ingredientId) throws SQLException {
        String sql = """
        SELECT iph.unit_price 
        FROM ingredient_price_history iph
        WHERE iph.id_ingredient = ? 
        ORDER BY iph.price_datetime DESC 
        LIMIT 1;
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ingredientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("unit_price");
            }
        }

        String fallbackSql = "SELECT unit_price FROM ingredient WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(fallbackSql)) {
            stmt.setString(1, ingredientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("unit_price");
            }
        }

        return BigDecimal.ZERO;
    }

}

