package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTests {
    private final FileReaderImpl fileReader = new FileReaderImpl();
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void read_ShouldReadFileSuccessufully() {
       String fileName = "reportToRead.csv";
       List<String> lines = fileReader.read(fileName);
       assertNotNull(lines);
       assertFalse(lines.isEmpty());
       assertEquals("type,fruit,quantity", lines.get(0));
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
}
