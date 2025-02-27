package repository;

import entity.Dish;
import entity.Ingredient;
import entity.PriceHistory;
import entity.Unit;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishRepository implements Repository<Dish> {
    private final Connection connection;
    private final IngredientRepository ingredientRepository;

    public DishRepository(Connection connection) {
        this.connection = connection;
        this.ingredientRepository = new IngredientRepository(connection);
    }

    public Dish resultSetToDish(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        BigDecimal unitPrice = rs.getBigDecimal("unit_price");

        List<Ingredient> ingredients = getIngredientsByDishId(id);

        return new Dish(id, name, unitPrice, ingredients, connection);
    }

    public List<Ingredient> getIngredientsByDishId(String dishId) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = """
    SELECT 
        i.id, 
        i.name, 
        i.update_datetime, 
        i.unit_price, 
        i.unit, 
        ph.id AS ph_id, 
        ph.price_datetime, 
        ph.unit_price AS price_value,
        ph.id_ingredient  -- Assure-toi que cette colonne est bien récupérée
    FROM dish_ingredient di
    JOIN ingredient i ON di.id_ingredient = i.id
    LEFT JOIN ingredient_price_history ph ON i.id = ph.id_ingredient
    WHERE di.id_dish = ?
    """;

        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, dishId);
            ResultSet rs = st.executeQuery();

            Map<String, Ingredient> ingredientMap = new HashMap<>();

            while (rs.next()) {
                String ingredientId = rs.getString("id");
                Ingredient ingredient = ingredientMap.get(ingredientId);

                if (ingredient == null) {
                    // Créer un nouvel ingrédient si c'est la première fois qu'on le rencontre
                    ingredient = new Ingredient(
                            ingredientId,
                            rs.getString("name"),
                            rs.getTimestamp("update_datetime").toLocalDateTime(),
                            rs.getBigDecimal("unit_price"),
                            Unit.valueOf(rs.getString("unit")),
                            new ArrayList<>() // Historique des prix vide au départ
                    );
                    ingredientMap.put(ingredientId, ingredient);
                }

                // Ajouter un PriceHistory pour cet ingrédient si disponible
                String phId = rs.getString("ph_id");
                String phIdIngredient = rs.getString("id_ingredient"); // Récupérer id_ingredient
                if (phId != null) {
                    PriceHistory priceHistory = new PriceHistory(
                            phId,
                            rs.getString("id_ingredient"),
                            rs.getTimestamp("price_datetime").toLocalDateTime(),
                            rs.getBigDecimal("price_value")
                    );
                    ingredient.getPriceHistory().add(priceHistory);
                }
            }

            // Convertir les valeurs de la map en liste
            ingredients.addAll(ingredientMap.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredients;
    }


    public Dish findById(String id) {
        String query = "select * from \"dish\" where \"id\" = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return resultSetToDish(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Dish> findAll(Pagination pagination, Order order) {
        List<Ingredient> ingredients = new ArrayList<>() ;
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