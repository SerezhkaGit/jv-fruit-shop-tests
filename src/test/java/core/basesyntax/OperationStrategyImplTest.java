package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private OperationHandler testHandler;

    @BeforeEach
    void setUp() {
        testHandler = transaction -> {};
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, testHandler);
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_ShouldReturnHandler_WhenOperationExists() {
        OperationHandler result = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(testHandler, result);
    }

    @Test
    void get_ShouldThrowException_WhenOperationNotFound() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.get(FruitTransaction.Operation.PURCHASE));
    }
}
