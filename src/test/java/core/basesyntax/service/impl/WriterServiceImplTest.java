package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WriterServiceImplTest {
    private static final String DATA_TO_WRITE = "fruit,quantity";
    private static final String VALID_PATH = "testFile.csv";
    private static final String INVALID_PATH = "\0invalid:path";
    private final WriterService writerService = new WriterServiceImpl();

    @TempDir
    private Path tempDir;

    @Test
    void write_validData_ok() throws IOException {
        File tempFile = tempDir.resolve(VALID_PATH).toFile();
        writerService.write(DATA_TO_WRITE, tempFile.getAbsolutePath());
        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        assertEquals(DATA_TO_WRITE, reader.readLine());
    }

    @Test
    void write_invalidPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                writerService.write(DATA_TO_WRITE, INVALID_PATH));
    }
}
