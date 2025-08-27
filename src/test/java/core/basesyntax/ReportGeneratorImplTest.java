package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        reportGenerator = new ReportGeneratorImpl();
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
