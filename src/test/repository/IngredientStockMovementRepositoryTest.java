package test.repository;

import entity.Ingredient;
import entity.IngredientStockMovement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IngredientRepository;
import repository.IngredientStockMovementRepository;
import repository.PriceHistoryRepository;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import static entity.IngredientStockMovementType.IN;
import static entity.IngredientStockMovementType.OUT;
import static entity.Unit.G;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.utils.IngredientTestDataUtils.*;

class IngredientStockMovementRepositoryTest {
    private final DatabaseConnection dbConnection = new DatabaseConnection();
    private final Connection connection = dbConnection.getConnection();

    private final IngredientStockMovementRepository subject = new IngredientStockMovementRepository(connection);
    private final PriceHistoryRepository priceHistoryRepository = new PriceHistoryRepository(connection);
    private final IngredientRepository ingredientRepository = new IngredientRepository(connection);
    private final IngredientStockMovementRepository ingredientStockMovementRepository = new IngredientStockMovementRepository(connection);

    @BeforeEach()
    void beforeEach(){
        this.ingredientRepository.create(sel());
        this.ingredientRepository.create(riz());

        sel().getPriceHistories().forEach(this.priceHistoryRepository::create);
        riz().getPriceHistories().forEach(this.priceHistoryRepository::create);
    }

    @AfterEach()
    void afterEach(){
        riz().getPriceHistories().forEach(priceHistory -> {
            this.priceHistoryRepository.deleteById(priceHistory.getId());
        });
        sel().getPriceHistories().forEach(priceHistory -> {
            this.priceHistoryRepository.deleteById(priceHistory.getId());
        });

        sel().getIngredientStocks().forEach(ingredientStockMovement ->  this.ingredientStockMovementRepository.deleteById(ingredientStockMovement.id()));
        riz().getIngredientStocks().forEach(ingredientStockMovement ->  this.ingredientStockMovementRepository.deleteById(ingredientStockMovement.id()));

        this.ingredientRepository.deleteById(sel().getId());
        this.ingredientRepository.deleteById(riz().getId());
    }

    void saveStocks(Ingredient ingredient){
        sel().getIngredientStocks().forEach(this.ingredientStockMovementRepository::create);
    }

    @Test
    void sel_movement_stock_default(){
        Ingredient sel = sel();
        sel.setIngredientStocks(List.of(
            new IngredientStockMovement("S0010", "I005", 5f, LocalDateTime.now(), IN, G),
            new IngredientStockMovement("S0011", "I005", 10f, LocalDateTime.now(), OUT, G),
            new IngredientStockMovement("S0012", "I005", 20f, LocalDateTime.now(), IN, G),
            new IngredientStockMovement("S0013", "I005", 5f, LocalDateTime.now(), IN, G),
            new IngredientStockMovement("S0014", "I005", 5f, LocalDateTime.now(), OUT, G)
        ));
        saveStocks(sel);
        Float expected = 15f;

        Float actual = sel.getAvailableQuantity();

        assertEquals(expected, actual);
    }

    @Test
    void riz_movement_stock_default(){
        Ingredient riz = riz();
        riz.setIngredientStocks(List.of(
            new IngredientStockMovement("S0015", "I005", 15f, LocalDateTime.now(), IN, G),
            new IngredientStockMovement("S0016", "I005", 10f, LocalDateTime.now(), OUT, G)
        ));
        saveStocks(riz);
        Float expected = 5f;

        Float actual = riz.getAvailableQuantity();

        assertEquals(expected, actual);
    }
}
