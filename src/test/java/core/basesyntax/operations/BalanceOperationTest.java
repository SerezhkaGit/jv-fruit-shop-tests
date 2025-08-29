package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void clear() {
        Storage.fruits.clear();
    }

    @Test
    void apply_ShouldSetBalanceCorrectly() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50);

        balanceOperation.apply(transaction);

        assertEquals(50, Storage.fruits.get("apple"));
    }

    @Test
    void apply_ShouldOverrideOldValue() {
        Storage.fruits.put("apple", 30);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);

        balanceOperation.apply(transaction);

        assertEquals(100, Storage.fruits.get("apple"));
    }
}
