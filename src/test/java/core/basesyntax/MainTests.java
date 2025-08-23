package core.basesyntax;

import core.basesyntax.service.impl.FileReaderImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTests {
    private final FileReaderImpl fileReader = new FileReaderImpl();

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

}
