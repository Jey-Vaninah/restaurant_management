package repository;

import entity.Ingredient;
import entity.PriceHistory;
import entity.Unit;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepository implements Repository<Ingredient> {
    final private Connection connection;

    public IngredientRepository(Connection connection) {
        this.connection = connection;
    }

    private Ingredient resultSetToIngredient(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        LocalDateTime updateDateTime = rs.getTimestamp("update_datetime").toLocalDateTime();
        Unit unit = Unit.valueOf(rs.getString("unit"));
        List<PriceHistory> priceHistory = this.getPriceHistoryByIngredientId(id);
        BigDecimal unitPrice = rs.getBigDecimal("unit_price");
        return new Ingredient(
                id,
                name,
                updateDateTime,
                unitPrice,
                unit,
                priceHistory
        );
    }

    public List<PriceHistory> getPriceHistoryByIngredientId(String ingredientId) {
        List<PriceHistory> priceHistories = new ArrayList<>();
        String query = "SELECT * FROM ingredient_price_history WHERE id_ingredient = ? ORDER BY price_datetime DESC";

        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, ingredientId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                priceHistories.add(new PriceHistory(
                        rs.getString("id"),
                        rs.getString("id_ingredient"),
                        rs.getTimestamp("price_datetime").toLocalDateTime(),
                        rs.getBigDecimal("unit_price")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return priceHistories;
    }


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
       public List<Ingredient> findAll(Pagination pagination, Order order){
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
            insert into "ingredient"("id", "name", "update_datetime", "unit_price", "unit")
            values (?, ?, ?, ?, ?);
         """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toCreate.getId());
            prs.setString (2, toCreate.getName());
            prs.setTimestamp(3, Timestamp.valueOf(toCreate.getUpdateDatetime()));
            prs.setBigDecimal (4, toCreate.getUnitPrice());
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
                    "unit_price" = ?,
                    "unit" = ?
                where "id" = ?
        """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toUpdate.getName());
            prs.setTimestamp(2, Timestamp.valueOf(toUpdate.getUpdateDatetime()));
            prs.setBigDecimal(3, toUpdate.getUnitPrice());
            prs.setString (4, toUpdate.getUnit().toString());
            prs.setString (5, toUpdate.getId());
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

    public List<Ingredient> findByCriteria(List<Criteria> criteria, Order order, Pagination pagination) {
        List<Ingredient> ingredients = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT i.id, i.name, i.unit, i.unit_price, i.update_datetime FROM ingredient i WHERE 1=1");
        List<Object> parameters = new ArrayList<>();
        Timestamp updateDatetimeFrom = null;
        Timestamp updateDatetimeEnd = null;
        BigDecimal priceFrom = null;
        BigDecimal priceTo = null;

        for (Criteria c : criteria) {
            if ("name".equals(c.getColumn())) {
                sql.append(" AND i.").append(c.getColumn()).append(" ilike ?");
                parameters.add("%" + c.getValue().toString() + "%");
            } else if("unit_price_from".equals(c.getColumn())) {
                priceFrom = (BigDecimal) c.getValue();
            } else if("unit_price_to".equals(c.getColumn())) {
                priceTo = (BigDecimal) c.getValue();
            } else if ("update_datetime _from".equals(c.getColumn())) {
                updateDatetimeFrom = (Timestamp) c.getValue();
            }else if("update_datetime _end".equals(c.getColumn())) {
                updateDatetimeEnd = (Timestamp) c.getValue();
            } else if ("unit".equals(c.getColumn())) {
                sql.append(" AND i.").append(c.getColumn()).append(" = ?");
                parameters.add(c.getValue());
            }
        }

        if(updateDatetimeFrom != null && updateDatetimeEnd != null) {
            sql.append(" and i.update_datetime between ? and ?");
            parameters.add(updateDatetimeFrom);
            parameters.add(updateDatetimeEnd);
        }

        if(priceFrom != null && priceTo != null) {
            sql.append(" and i.unit_price between ? and ?");
            parameters.add(priceFrom);
            parameters.add(priceTo);
        }

        sql.append(" order by i.").append(order.getOrderBy()).append(" ").append(order.getOrderValue());
        sql.append(" limit ").append(pagination.getPageSize()).append(" offset ").append(
                (pagination.getPage()  - 1) * pagination.getPageSize()
        );

        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parameters.size(); i++) {
                Object value = parameters.get(i);
                if(value.getClass().isEnum()){
                    statement.setObject(i  + 1, value, Types.OTHER);
                    continue;
                }
                statement.setObject(i + 1, value);
            }
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                ingredients.add(resultSetToIngredient(rs));
            }
        } catch (SQLException error) {
            throw new RuntimeException(error.getMessage());
        }
        return ingredients;
    }
}

