package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new EnumMap<>(
                FruitTransaction.Operation.class);
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy operationStrategy = handlerMap::get;
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_ShouldApplyBalanceAndPurchaseCorrectly() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20)
        );

        shopService.process(transactions);

        assertEquals(30, Storage.fruits.get("apple"));
    }

    @Test
    void process_ShouldApplySupplyCorrectly() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 25)
        );

        shopService.process(transactions);

        assertEquals(35, Storage.fruits.get("banana"));
    }

    @Test
    void process_ShouldApplyReturnCorrectly() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 40),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 10)
        );

        shopService.process(transactions);

        assertEquals(50, Storage.fruits.get("orange"));
    }
}
