package repository;

import entity.Ingredient;
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

    private Ingredient resultSetToIngredient(ResultSet rs, LocalDateTime datetime) throws SQLException {
        String id = rs.getString("id");
        return new Ingredient(
            id,
            rs.getString("name"),
            rs.getTimestamp("updated_datetime").toLocalDateTime(),
            this.getIngredientPriceById(id, datetime),
            Unit.valueOf(rs.getString("unit"))
        );
    }

    public List<Ingredient> findByDishId(String dishId, LocalDateTime datetime){
        String query = """
            select
                i.id as id,
                i.name as name,
                i.update_datetime as update_datetime,
                i.unit as unit,
                i.unit_price as unit_price
            from ingredient i
            inner join dish_ingredient di on i.id = di.id_ingredient
            where di.dish_ingredient = ?;
        """;

        List<Ingredient> ingredients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dishId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                ingredients.add(resultSetToIngredient(rs, datetime));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public Ingredient findById(String id, LocalDateTime datetime) {
        String query = "SELECT * FROM \"ingredient\" WHERE \"id\" = ?";
        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return resultSetToIngredient(rs, datetime);
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient findById(String id) {
        return this.findById(id, LocalDateTime.now());
    }

    public List<Ingredient> findAll(Pagination pagination, Order order, LocalDateTime datetime) {
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
                ingredients.add(resultSetToIngredient(rs, datetime));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    @Override
    public List<Ingredient> findAll(Pagination pagination, Order order) {
        return this.findAll(pagination, order, LocalDateTime.now());
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
                    "unit_price" = ?,
                    "unit" = ?
                where "id" = ?
        """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toUpdate.getName());
            prs.setTimestamp(2, Timestamp.valueOf(toUpdate.getUpdatedDatetime()));
            prs.setBigDecimal(3, toUpdate.getPrice());
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

    public BigDecimal getIngredientPriceById(String ingredientId){
        return getIngredientPriceById(ingredientId, LocalDateTime.now());
    }

    public BigDecimal getIngredientPriceById(String ingredientId, LocalDateTime datetime){
        String sql = """
            SELECT iph.unit_price
            FROM ingredient_price_history iph
            WHERE
                iph.id_ingredient = ?
                AND iph.price_datetime <= ?
            ORDER BY iph.price_datetime DESC
            LIMIT 1;
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ingredientId);
            statement.setTimestamp(2, Timestamp.valueOf(datetime));
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("unit_price");
            }

            return BigDecimal.ZERO;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Ingredient> findByCriteria(List<Criteria> criteria, Order order, Pagination pagination) {
        return this.findByCriteria(criteria, order, pagination, LocalDateTime.now());
    }

    public List<Ingredient> findByCriteria(List<Criteria> criteria, Order order, Pagination pagination, LocalDateTime datetime) {
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
                ingredients.add(resultSetToIngredient(rs, datetime));
            }
        } catch (SQLException error) {
            throw new RuntimeException(error.getMessage());
        }
        return ingredients;
    }
}

