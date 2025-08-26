package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    @Test
    void get_ShouldReturnCorrectHandler() {
        Map<FruitTransaction.Operation, OperationHandler> handlerMap =
                new EnumMap<>(FruitTransaction.Operation.class);
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlerMap);

        assertEquals(BalanceOperation.class,
                strategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    void get_ShouldThrowException_WhenHandlerMissing() {
        OperationStrategy strategy = new OperationStrategyImpl(new EnumMap<>(
                FruitTransaction.Operation.class));

        assertThrows(RuntimeException.class,
                () -> strategy.get(FruitTransaction.Operation.SUPPLY));
    }
}
