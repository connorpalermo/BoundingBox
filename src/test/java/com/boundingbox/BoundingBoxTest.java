package com.boundingbox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class BoundingBoxTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testProcessGroups() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("groups.txt")) {
            assertNotNull(inputStream, "Resource not found");
            BoundingBox.process(inputStream);
            Assertions.assertEquals("(1,1)(2,2)", outputStreamCaptor.toString()
                    .trim());
        } catch (Exception e) {
            fail("Exception occurred while processing the file: " + e.getMessage());
        }
    }

    /**
     * Validate that we handle the expected IOException
     */
    @Test
    public void testProcess_IOException() {
        InputStream exceptionInputStream = new InputStream() {
            @Override
            public int read() {
                return -1;
            }

            @Override
            public void close() throws IOException {
                throw new IOException("Test IOException");
            }
        };

        try {
            BoundingBox.process(exceptionInputStream);
        } catch (Exception e) {
            fail("Exception should not be thrown from process method: " + e.getMessage());
        }
    }

    @Test
    public void testProcess_UnexpectedException() {
        InputStream exceptionInputStream = new InputStream() {
            @Override
            public int read() throws ArithmeticException {
                throw new ArithmeticException("Test ArithmeticException");
            }
        };

        try {
            BoundingBox.process(exceptionInputStream);
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Test ArithmeticException"));
        }
    }
}
