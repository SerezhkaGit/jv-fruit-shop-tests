package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    void constructorAndGetters_ShouldWorkCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 15);

        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(15, transaction.getQuantity());
    }
}
