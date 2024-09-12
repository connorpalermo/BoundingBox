package com.boundingbox.core;

import com.boundingbox.dto.BoxCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/***
 * MinBoundingBox - the core logic for finding the minimum bounding boxes.
 */
public class MinBoundingBox {
    private final char[][] input;
    private final List<BoxCoordinates> minBoxCoordinates;
    private final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private final int INDEX_OFFSET = 1;
    private int minBounding = Integer.MAX_VALUE;

    private static final Logger logger = LoggerFactory.getLogger(MinBoundingBox.class);

    public MinBoundingBox(char[][] input) {
        this.minBoxCoordinates = new ArrayList<>();
        this.input = input;
    }

    /**
     * Processes the input matrix
     *
     * @return List of minimum bounding box coordinates
     */
    public List<BoxCoordinates> process() {
        logger.debug("extracting minimum bounding boxes");
        extractMinBoundingBoxes(input);
        logger.debug("extracted minimum bounding boxes");
        return minBoxCoordinates;
    }

    /**
     * Extracts the minimum bounding boxes using the
     * Breadth First Search (BFS) Algorithm
     *
     * @param input The char matrix input
     */
    private void extractMinBoundingBoxes(char[][] input) {
        // Traverse each cell in the input matrix
        // If a '*' is found, start BFS to find the size of current box
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[row].length; col++) {
                if (input[row][col] == '*') {
                    // start BFS from the current cell.
                    logger.debug("starting BFS from coordinates ({}, {})", row, col);
                    BoxCoordinates coordinates = new BoxCoordinates(row, col,
                            row, col);
                    int size = traverse(input, row, col, coordinates);
                    // increment by 1 because input is one-indexed
                    coordinates.incrementByOffset(INDEX_OFFSET);
                    // update the coordinates list based off of the minBounding value
                    if (size < minBounding) {
                        minBounding = size;
                        minBoxCoordinates.clear();
                        minBoxCoordinates.add(coordinates);
                    } else if (size == minBounding) {
                        minBoxCoordinates.add(coordinates);
                    }
                }
            }
        }
    }

    /**
     * Run the BFS traversal from the starting point
     *
     * @param input       The char matrix input
     * @param row         The integer row for the BFS starting point
     * @param col         The integer column for the BFS starting point
     * @param coordinates The BoxCoordinates object used to track beginning and end coordinates
     * @return int size of the current box
     */
    private int traverse(char[][] input, int row, int col, BoxCoordinates coordinates) {
        Queue<int[]> bfs = new LinkedList<>();
        bfs.add(new int[]{row, col});
        // mark as X to signify this cell was visited and will not
        // be visited again
        input[row][col] = 'X';
        int size = 0;

        while (!bfs.isEmpty()) {
            int[] currentCell = bfs.poll();
            int currRow = currentCell[0];
            int currCol = currentCell[1];

            size++;

            // update the coordinates accordingly so that they accurately reflect
            // bottom right points
            if (currRow >= coordinates.getBottomX() && currCol >= coordinates.getBottomY()) {
                coordinates.setBottomX(currRow);
                coordinates.setBottomY(currCol);
            }

            // process the neighbors of the current cell
            processDirections(input, currRow, currCol, bfs);

        }
        return size;
    }

    /**
     * Add all valid neighbors for the current row and column to the
     * BFS Queue
     *
     * @param input The char matrix input
     * @param row   The current row
     * @param col   The current column
     * @param bfs   The Breadth First Search queue
     */
    private void processDirections(char[][] input, int row, int col, Queue<int[]> bfs) {
        // only add the neighbor if they have not been visited and are an asterisk
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newCol >= 0 && newRow < input.length
                    && newCol < input[0].length && input[newRow][newCol] == '*') {
                bfs.add(new int[]{newRow, newCol});
                input[newRow][newCol] = 'X';
            }
        }
    }
}
