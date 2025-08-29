package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void clear() {
        Storage.fruits.clear();
    }

    @Test
    void getReport_ShouldReturnFormattedReport() {
        Storage.fruits.put("apple", 50);
        Storage.fruits.put("banana", 30);

        String report = reportGenerator.getReport();

        assertTrue(report.contains("apple,50"));
        assertTrue(report.contains("banana,30"));
        assertTrue(report.startsWith("fruit,quantity"));
    }
}
