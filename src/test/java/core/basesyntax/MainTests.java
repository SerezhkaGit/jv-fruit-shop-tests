package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTests {
    private final FileReaderImpl fileReader = new FileReaderImpl();
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @AfterEach
    void clearCache() {
        Storage.fruits.clear();
    }

    @Test
    void read_ShouldReadFileSuccessufully() {
        String fileName = "reportToRead.csv";
        List<String> lines = fileReader.read(fileName);
        assertNotNull(lines);
        assertFalse(lines.isEmpty());
        assertEquals("type,fruit,quantity", lines.get(0));
    }

    @Test
    void main_ShouldRunWithoutExceptions() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    void read_ShouldThrowException_WhenFileDoesNotExist() {
        String wrongFile = "not-exist.txt";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(wrongFile));
        assertTrue(exception.getMessage().contains("Can't read file"));
    }

    @Test
    void write_ShouldWriteInfoToFileSuccessfully() throws IOException {

        String report = "fruit,quantity\napple,10\nbanana,5";
        Path tempFile = Files.createTempFile("finalReport", ".csv");

        fileWriter.write(report, tempFile.toString());

        String actual = Files.readString(tempFile);
        assertEquals(report, actual);

        Files.deleteIfExists(tempFile);
    }

    @Test
    void write_ShouldThrowException_WhenFilePathInvalid() {
        String report = "test data";
        String invalidPath = "/invalid-path/finalReport.csv";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write(report, invalidPath));

        assertTrue(exception.getMessage().contains("Can't write to file"));
    }

    @Test
    void convertToTransaction_ShouldReturnTransaction_WhenInputValid() {
        DataConverterImpl converter = new DataConverterImpl();
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,50",
                "p,apple,20",
                "r,banana,10"
        );
        List<FruitTransaction> result = converter.convertToTransaction(lines);
        assertEquals(4, result.size());

        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.RETURN, result.get(3).getOperation());
        assertEquals("banana", result.get(3).getFruit());
        assertEquals(10, result.get(3).getQuantity());
    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenLineHasWrongPartsCount() {
        DataConverterImpl converter = new DataConverterImpl();
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b, apple"
        );

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(lines));
        assertTrue(exception.getMessage().contains("Invalid line format"));

    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenOperationInvalid() {
        DataConverterImpl converter = new DataConverterImpl();
        List<String> lines = List.of(
                "type,fruit,quantity",
                "x, apple, 20"
        );

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(lines));
        assertTrue(exception.getMessage().contains("Invalid operation"));

    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenFruitIsEmpty() {
        DataConverterImpl converter = new DataConverterImpl();
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,,10"
        );

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(lines));
        assertTrue(ex.getMessage().contains("Fruit name cannot be null or empty"));
    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenQuantityIsNegative() {
        DataConverterImpl converter = new DataConverterImpl();
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,apple,-5"
        );
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(lines));
        assertTrue(exception.getMessage().contains("Quantity cannot be negative"));
    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenQuantityNotNumber() {
        DataConverterImpl converter = new DataConverterImpl();
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,apple,ten"
        );

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(lines));
        assertTrue(ex.getMessage().contains("Invalid quantity format"));
    }

    @Test
    void getReport_ShouldReturnOnlyHeader_WhenStorageIsEmpty() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_ShouldReturnReportWithOneFruit() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        Storage.fruits.put("apple", 50);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator();
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }

    @Test
    void storage_ShouldStoreFruits() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 10);
        assertEquals(10, Storage.fruits.get("apple"));
    }


}


