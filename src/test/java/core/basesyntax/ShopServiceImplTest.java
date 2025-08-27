package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE,
                transaction -> Storage.fruits.put(transaction.getFruit(),
                        transaction.getQuantity()));
        OperationStrategy operationStrategy = new core.basesyntax.strategy
                .OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_ShouldApplyHandlerCorrectly() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);

        shopService.process(Collections.singletonList(transaction));

        assertEquals(100, Storage.fruits.get("apple"));
    }
}
