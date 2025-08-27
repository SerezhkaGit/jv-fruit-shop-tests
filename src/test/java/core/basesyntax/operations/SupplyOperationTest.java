package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        supplyOperation = new SupplyOperation();
    }

    @Test
    void apply_ShouldAddToEmptyStorage() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 40);

        supplyOperation.apply(transaction);

        assertEquals(40, Storage.fruits.get("banana"));
    }

    @Test
    void apply_ShouldIncreaseQuantityIfFruitAlreadyExists() {
        Storage.fruits.put("banana", 10);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);

        supplyOperation.apply(transaction);

        assertEquals(30, Storage.fruits.get("banana"));
    }
}

