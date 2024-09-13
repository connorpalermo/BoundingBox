package com.boundingbox.core;

import com.boundingbox.dto.BoxCoordinates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinBoundingBoxTest {
    private static final char[][] VALID_INPUT_ONE_MIN_BOX = {
            {'*', '*', '-', '-', '-', '-', '-', '-', '-', '*', '*', '*'},
            {'-', '*', '-', '-', '*', '*', '-', '-', '*', '*', '*', '-'},
            {'-', '-', '-', '-', '-', '*', '*', '*', '-', '-', '*', '*'},
            {'-', '-', '-', '-', '-', '-', '-', '*', '*', '*', '-', '-'}
    };

    private static final char[][] VALID_INPUT_MULTIPLE_MIN_BOXES = {
            {'*', '*', '-', '-', '-', '-', '-', '-', '-', '*', '*', '*'},
            {'-', '*', '-', '-', '-', '*', '-', '-', '*', '*', '*', '-'},
            {'-', '-', '-', '-', '-', '*', '*', '-', '-', '-', '*', '*'},
            {'-', '-', '-', '-', '-', '-', '-', '*', '*', '*', '-', '-'}
    };

    private static final char[][] VALID_INPUT_EDGE_CASE_MIN_BOXES = {
            {'-', '*', '*', '*', '-', '-', '-', '-', '-', '*', '*', '*'},
            {'-', '*', '-', '-', '*', '*', '-', '-', '*', '*', '*', '-'},
            {'-', '-', '-', '-', '-', '*', '*', '-', '-', '-', '*', '*'},
            {'-', '-', '-', '-', '-', '-', '*', '*', '*', '*', '-', '-'}
    };

    private static final char[][] VALID_INPUT_SINGLE_COORDINATE_MIN_BOX = {
            {'-', '-', '-', '-', '-', '-'},
            {'-', '-', '*', '-', '-', '-'},
            {'-', '-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-', '-'}
    };

    @Test
    public void test_OneMinBoundingBox() {
        BoxCoordinates coordinate = new BoxCoordinates(1, 1, 2, 2);
        MinBoundingBox minBoundingBox = new MinBoundingBox(VALID_INPUT_ONE_MIN_BOX);
        List<BoxCoordinates> coordinates = minBoundingBox.process();

        Assertions.assertEquals(1, coordinates.size());
        Assertions.assertEquals(coordinates.get(0), coordinate,
                () -> "expected: " + coordinate + ", actual: " + coordinates.get(0));
    }

    @Test
    public void test_MultipleMinBoundingBox() {
        List<BoxCoordinates> expectedCoordinates = new ArrayList<>();
        expectedCoordinates.add(new BoxCoordinates(1, 1, 2, 2));
        expectedCoordinates.add(new BoxCoordinates(2, 6, 3, 7));
        expectedCoordinates.add(new BoxCoordinates(4, 8, 4, 10));
        MinBoundingBox minBoundingBox = new MinBoundingBox(VALID_INPUT_MULTIPLE_MIN_BOXES);
        List<BoxCoordinates> coordinates = minBoundingBox.process();

        Assertions.assertEquals(3, coordinates.size());
        for (int i = 0; i < coordinates.size(); i++) {
            Assertions.assertEquals(coordinates.get(i), expectedCoordinates.get(i),
                    "Mismatch at index " + i);
        }
    }

    /**
     * This is an edge case because the min box has more right columns
     * on the top than it does on the bottom. The logic ensures
     * that bottom right coordinates are only updated if both
     * the current row and column are greater than or equal to
     * the current coordinates.
     */
    @Test
    public void test_EdgeCaseMinBoundingBox() {
        BoxCoordinates coordinate = new BoxCoordinates(1, 2, 2, 2);
        MinBoundingBox minBoundingBox = new MinBoundingBox(VALID_INPUT_EDGE_CASE_MIN_BOXES);
        List<BoxCoordinates> coordinates = minBoundingBox.process();

        Assertions.assertEquals(1, coordinates.size());
        Assertions.assertEquals(coordinates.get(0), coordinate,
                () -> "expected: " + coordinate + ", actual: " + coordinates.get(0));
    }

    @Test
    public void test_SingleCoordinateMinBoundingBox() {
        BoxCoordinates coordinate = new BoxCoordinates(2, 3, 2, 3);
        MinBoundingBox minBoundingBox = new MinBoundingBox(VALID_INPUT_SINGLE_COORDINATE_MIN_BOX);
        List<BoxCoordinates> coordinates = minBoundingBox.process();

        Assertions.assertEquals(1, coordinates.size());
        Assertions.assertEquals(coordinates.get(0), coordinate,
                () -> "expected: " + coordinate + ", actual: " + coordinates.get(0));
    }

    @Test
    public void test_NoMinBoundingBox() {
        char[][] noMinBoxInput = new char[10][10];
        for (char[] row : noMinBoxInput) {
            Arrays.fill(row, '-');
        }
        MinBoundingBox minBoundingBox = new MinBoundingBox(noMinBoxInput);
        List<BoxCoordinates> coordinates = minBoundingBox.process();

        Assertions.assertEquals(0, coordinates.size());
    }

    @Test
    public void test_LargeBoundingBox() {
        BoxCoordinates coordinate = new BoxCoordinates(1, 1, 1000, 1000);
        char[][] largeInput = new char[1000][1000];
        for (char[] row : largeInput) {
            Arrays.fill(row, '*');
        }
        MinBoundingBox minBoundingBox = new MinBoundingBox(largeInput);
        List<BoxCoordinates> coordinates = minBoundingBox.process();
        Assertions.assertEquals(1, coordinates.size());
        Assertions.assertEquals(coordinates.get(0), coordinate,
                () -> "expected: " + coordinate + ", actual: " + coordinates.get(0));
    }

    @Test
    public void test_EmptyInput() {
        char[][] emptyInput = new char[0][0];
        MinBoundingBox minBoundingBox = new MinBoundingBox(emptyInput);
        List<BoxCoordinates> coordinates = minBoundingBox.process();
        Assertions.assertEquals(0, coordinates.size());
    }

}
