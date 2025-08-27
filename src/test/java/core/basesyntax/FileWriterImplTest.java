package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_ShouldCreateFileWithContent() throws IOException {
        String fileName = "test_output.csv";
        String content = "test content";

        fileWriter.write(content, fileName);

        String result = Files.readString(Paths.get(fileName));
        assertEquals(content, result);
    }

    @Test
    void write_ShouldThrowException_WhenInvalidPath() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("data", "/invalid_path/test.csv"));
    }
}
