package com.boundingbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.boundingbox.core.MinBoundingBox;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * BoundingBox - Entry point for processing input to find minimum bounding boxes.
 */
public class BoundingBox {

    private static final Logger logger = LoggerFactory.getLogger(BoundingBox.class);

    public static void main(String[] args) {
        // begin processing with standard input
        process(System.in);
    }

    /**
     * Processes the input stream to compute and print the minimum bounding boxes.
     *
     * @param input The input stream containing the data to process.
     */
    public static void process(InputStream input) {
        logger.debug("processing input file");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            char[][] lines = reader.lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
            logger.debug("processed file successfully");

            MinBoundingBox bound = new MinBoundingBox(lines);
            bound.process().forEach(box -> System.out.println(box.toString()));
        } catch (IOException e) {
            logger.error("IOException occurred: {}", e.getMessage());
        }
    }
}
