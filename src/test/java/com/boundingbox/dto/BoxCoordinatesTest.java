package com.boundingbox.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoxCoordinatesTest {

    @Test
    public void test_BoxCoordinatesEqualsAndHash(){
        BoxCoordinates coordinate = new BoxCoordinates(1, 1, 2, 2);
        BoxCoordinates coordinate2 = new BoxCoordinates(1, 1, 2, 2);
        BoxCoordinates coordinate3 = new BoxCoordinates(1, 1, 3, 3);

        Assertions.assertEquals(coordinate, coordinate2);
        Assertions.assertEquals(coordinate.hashCode(), coordinate2.hashCode());
        Assertions.assertNotEquals(coordinate2, coordinate3);
        Assertions.assertNotEquals(coordinate2.hashCode(), coordinate3.hashCode());
    }

    @Test
    public void test_BoxCoordinatesGetters() {
        BoxCoordinates coordinates = new BoxCoordinates(1, 1, 2, 2);
        Assertions.assertEquals(1, coordinates.getTopX());
        Assertions.assertEquals(1, coordinates.getTopY());
        Assertions.assertEquals(2, coordinates.getBottomX());
        Assertions.assertEquals(2, coordinates.getBottomY());
    }

    @Test
    public void test_IncrementByOffset(){
        BoxCoordinates coordinates = new BoxCoordinates(1, 1, 2, 2);
        coordinates.incrementByOffset(2);
        Assertions.assertEquals(3, coordinates.getTopX());
        Assertions.assertEquals(3, coordinates.getTopY());
        Assertions.assertEquals(4, coordinates.getBottomX());
        Assertions.assertEquals(4, coordinates.getBottomY());
    }


}
