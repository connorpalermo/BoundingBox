package com.boundingbox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    public String toString(){
        return "(" + topX + "," + topY + ")" + "(" + bottomX + "," + bottomY + ")";
    }

    public void incrementByOffset(int offset){
        topX+=offset;
        topY+=offset;
        bottomX+=offset;
        bottomY+=offset;
    }

}
