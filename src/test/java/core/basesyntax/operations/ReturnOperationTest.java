package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
    }

    @AfterEach
    void clear() {
        Storage.fruits.clear();
    }

    @Test
    void apply_ShouldAddFruitIfNotExist() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 15);

        returnOperation.apply(transaction);

        assertEquals(15, Storage.fruits.get("orange"));
    }

    @Test
    void apply_ShouldIncreaseQuantityIfFruitExists() {
        Storage.fruits.put("orange", 5);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 20);

        returnOperation.apply(transaction);

        assertEquals(25, Storage.fruits.get("orange"));
    }
}
