# BoundingBox Usage

To use the minimum bounding box tool, follow these steps:

## Build the Project

Run the following commands from the root folder to build the project:

```
mvn clean package
```

## Make the Script Executable

Make the bounding-box.sh script executable with:

```
chmod +x bounding-box.sh
```

## Run the Script

Run an example file using:

```
./bounding-box.sh < src/main/resources/groups.txt
```

## Example Files

There are two example files in the main/resources directory that coincide with the examples given in the take home
challenge. You can also create your own files, just be sure to provide the absolute path to the sh script.

## Logging

Info logs have been switched to ***debug*** to meet the following requirement.

**Running your program with this input would look something like this:**

```
> ./bounding-box < groups.txt
(1,1)(2,2)
```

Switch from debug to info and rebuild the project to see output when running script.

## Dependencies

**Java**: Be sure to have Java 11+ installed.

**Maven**: Be sure to have Maven 3+ installed.

## Future Considerations

**Input Validation**: Because the prompt explicitly states that files will be in a certain format, we do not need
validation here. However, if file formats and contents differ we could implement input validation (if board size is 0,
if file contents is invalid, etc).

**Set Up Continuous Integration**: We could set up continuous integration
through [GitHub Actions](https://docs.github.com/en/actions/use-cases-and-examples/building-and-testing/building-and-testing-java-with-maven) (
or another popular tool such as Jenkins) to automatically build and test our Bounding Box code.

## Notable Trade-Offs

**DFS vs. BFS**: We chose Breadth First Search (BFS) as our traversal algorithm because it is an iterative approach. In
comparison with recursive Depth First Search (DFS), there is significant improvement in speed of tests (~200ms) when
using BFS. In cases of large input files
(>= 1000 lines), the DFS threw a StackOverflowError. For these reasons, BFS seems to be the better choice.

**Visited Matrix vs. Flood Fill Approach**: We chose the flood fill approach in our BFS algorithm because there are no
requirements to not alter the input mentioned in the prompt. This way, we do not need to allocate another boolean "
visited matrix" to keep track of cells we already visited. This saves us space by not allocating another m * n 2d array,
where m is the amount of lines in our input file and n is the number of characters in each line.
