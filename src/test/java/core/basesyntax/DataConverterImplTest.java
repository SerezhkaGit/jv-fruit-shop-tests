package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_ShouldReturnValidTransactions() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,apple,10");
        lines.add("s,banana,20");

        List<FruitTransaction> result = dataConverter.convertToTransaction(lines);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenInvalidLine() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("invalid_line");

        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(lines));
    }
}
