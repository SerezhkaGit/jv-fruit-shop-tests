package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.PurchaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void apply_ShouldReduceQuantityCorrectly() {
        Storage.fruits.put("apple", 30);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10);

        purchaseOperation.apply(transaction);

        assertEquals(20, Storage.fruits.get("apple"));
    }

    @Test
    void apply_ShouldThrowException_WhenNotEnoughFruits() {
        Storage.fruits.put("apple", 5);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperation.apply(transaction));

        assertTrue(exception.getMessage().contains("Not enough apple in shop"));
    }

    @Test
    void apply_ShouldThrowException_WhenFruitNotInStorage() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "mango", 5);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperation.apply(transaction));

        assertTrue(exception.getMessage().contains("Not enough mango in shop"));
    }
}
