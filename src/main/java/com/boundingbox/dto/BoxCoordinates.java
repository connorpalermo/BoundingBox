package com.boundingbox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/***
 * BoxCoordinates - a DTO to simplify logic around BoundingBox start and end coordinates.
 */
@Getter
@Setter
@AllArgsConstructor
public class BoxCoordinates {
    private int topX;
    private int topY;
    private int bottomX;
    private int bottomY;

    @Override
    public String toString() {
        return "(" + topX + "," + topY + ")" + "(" + bottomX + "," + bottomY + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxCoordinates that = (BoxCoordinates) o;

        return topX == that.topX &&
                topY == that.topY &&
                bottomX == that.bottomX &&
                bottomY == that.bottomY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(topX, topY, bottomX, bottomY);
    }

    public void incrementByOffset(int offset) {
        topX += offset;
        topY += offset;
        bottomX += offset;
        bottomY += offset;
    }

}
