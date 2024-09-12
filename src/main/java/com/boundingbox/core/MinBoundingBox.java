package com.boundingbox.core;

import com.boundingbox.dto.BoxCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/***
 * BoundingBox - the core logic for finding the minimum bounding boxes.
 * @author cpalermo
 */
public class MinBoundingBox {
    private final char[][] input;
    private final List<BoxCoordinates> minBoxCoordinates;
    private final int[][] DIRECTIONS = {{1,0},{-1,0},{0,1},{0,-1}};
    private final int INDEX_OFFSET = 1;
    private int minBounding = Integer.MAX_VALUE;

    private static final Logger logger = LoggerFactory.getLogger(MinBoundingBox.class);

    public MinBoundingBox(char[][] input){
        this.minBoxCoordinates = new ArrayList<>();
        this.input = input;
    }

    /**
     * Processes the input matrix
     * @return List of minimum bounding box coordinates
     */
    public List<BoxCoordinates> process(){
        logger.debug("extracting minimum bounding boxes");
        extractMinBoundingBoxes(input);
        logger.debug("extracted minimum bounding boxes");
        return minBoxCoordinates;
    }

    /**
     * Extracts the minimum bounding boxes using the
     * Breadth First Search (BFS) Algorithm
     * @param input The char matrix input
     */
    private void extractMinBoundingBoxes(char[][] input){
        // Traverse each cell in the input matrix
        // If a '*' is found, start BFS to find the size of current box
        for(int row = 0; row < input.length; row++){
            for(int col = 0; col < input[row].length; col++){
                if(input[row][col] == '*'){
                    // start BFS from the current cell.
                    logger.debug("starting BFS from coordinates ({}, {})", row, col);
                    BoxCoordinates coordinates = new BoxCoordinates(row,col,
                                row,col);
                    int size = traverse(input, row, col, coordinates);
                    // increment by 1 because input is one-indexed
                    coordinates.incrementByOffset(INDEX_OFFSET);
                    // update the coordinates list based off of the minBounding value
                    if(size < minBounding){
                        minBounding = size;
                        minBoxCoordinates.clear();
                        minBoxCoordinates.add(coordinates);
                    }
                    else if(size == minBounding){
                        minBoxCoordinates.add(coordinates);
                    }
                }
            }
        }
    }

    /**
     *
     * @param input The char matrix input
     * @param row The integer row we are beginning BFS from
     * @param col The integer column we are beginning BFS from
     * @param coordinates The BoxCoordinates object used to track beginning and end coordinates
     * @return int size of the curring box we have traversed
     */
    private int traverse(char[][] input, int row, int col, BoxCoordinates coordinates){
        Queue<int[]> bfs = new LinkedList<>();
        bfs.add(new int[]{row,col});
        // mark as X to signify this cell was visited and will not
        // be visited again
        input[row][col] = 'X';
        int count = 0;

        while(!bfs.isEmpty()){
            int[] currentPos = bfs.poll();
            int currRow = currentPos[0];
            int currCol = currentPos[1];

            count++;

            // update the coordinates accordingly so that they accurately reflect
            // bottom right points
            if (currRow >= coordinates.getBottomX() && currCol >= coordinates.getBottomY()) {
                coordinates.setBottomX(currRow);
                coordinates.setBottomY(currCol);
            }

            // if adjacent neighboring cells are asterisks, we want to
            // traverse those too
            for(int[] dir : DIRECTIONS) {
                int newRow = currRow + dir[0];
                int newCol = currCol + dir[1];
                if (newRow >= 0 && newCol >= 0 && newRow < input.length
                        && newCol < input[0].length && input[newRow][newCol] == '*') {
                    bfs.add(new int[]{newRow, newCol});
                    input[newRow][newCol] = 'X';
                }
            }

        }
        return count;
    }
}
