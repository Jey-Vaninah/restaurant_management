package repository;

import entity.PriceHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PriceHistoryRepository implements Repository<PriceHistory>{
    final private Connection connection;

    public PriceHistoryRepository(Connection connection) {
        this.connection = connection;
    }

    private PriceHistory resultSetToPriceHistory(ResultSet rs) throws SQLException {
        return new PriceHistory(
            rs.getString("id"),
            rs.getString("id_ingredient"),
            rs.getTimestamp("price_datetime").toLocalDateTime(),
            rs.getBigDecimal("unit_price")
        );
    }

    public List<PriceHistory> findByIngredientId(String id){
        List<PriceHistory> priceHistories = new ArrayList<>();
        String query = """
            select *
                from "ingredient_price_history"
                where "id_ingredient" = ?
                order by "price_datetime" desc
        """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                priceHistories.add(resultSetToPriceHistory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return priceHistories;
    }

    @Override
    public PriceHistory findById(String id){
        throw new RuntimeException("no implemented");
    }

    @Override
    public List<PriceHistory> findAll(Pagination pagination, Order order) {
        throw new RuntimeException("no implemented");
    }

    @Override
    public PriceHistory deleteById(String id) {
        throw new RuntimeException("no implemented");
    }

    @Override
    public PriceHistory create(PriceHistory toCreate) {
        String query = """
            insert into "ingredient_price_history"("id", "id_ingredient", "price_datetime", "unit_price")
            values (?, ?, ?, ?);
        """;

        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toCreate.getId());
            prs.setString (2, toCreate.getIdIngredient());
            prs.setTimestamp(3, Timestamp.valueOf(toCreate.getPriceDatetime()));
            prs.setBigDecimal (4, toCreate.getUnitPrice());
            prs.executeUpdate();
            return this.findById(toCreate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public PriceHistory update(PriceHistory id) {
        throw new RuntimeException("no implemented");
    }

    @Override
    public PriceHistory crupdate(PriceHistory id) {
        throw new RuntimeException("no implemented");
    }
}
