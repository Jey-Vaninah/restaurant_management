package repository;

import entity.PriceHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class PriceHistoryRepository implements Repository<PriceHistory>{
    final private Connection connection;

    public PriceHistoryRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public PriceHistory findById(String id) {
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
    public PriceHistory create (PriceHistory toCreate) {
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
