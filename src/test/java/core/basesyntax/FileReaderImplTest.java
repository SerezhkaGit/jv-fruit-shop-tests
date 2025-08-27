package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_ShouldReturnFileContent() throws IOException {
        Path tempFile = Files.createTempFile("forTests", ".csv");
        Files.writeString(tempFile, "line1");

        List<String> lines = Files.readAllLines(tempFile);

        assertEquals(1, lines.size());
        assertEquals("line1", lines.get(0));

        Files.deleteIfExists(tempFile);
    }

    @Test
    void read_ShouldThrowException_WhenFileDoesNotExist() {
        assertThrows(RuntimeException.class, () -> fileReader.read("not_exist.csv"));
    }
}
