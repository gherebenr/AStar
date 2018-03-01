# AStar
Implementation of the A* pathfinding algorithm. 

### Getting Started
1) Run: ```javac *.java```. This will compile everything.
2) Run any of the following:

| Command       | Description   |
| :------------- |:-------------|
| ```java Main AStar```       | Run A* on a random map. |
| ```java Main AStar -seed <ANY NUMBER>```      | Run A* on a map with a specific seed.      |
| ```java Main AStar -load MTAFT.XYZ``` | Run A* on the Mt. St. Helens map.      |
| ```java Main AStar Dijkstra``` | Run multiple pathfinding algorithms for comparison.      |

### More Details
1) The cost is 2<sup>h2-h1</sup> to move by one node, where h1 is the height of the current node, and h2 is the height of the target node.
2) The red on the map represents all the explored nodes, and the blue represents the chosen path.

## I only wrote AStar.java and Dijkstra.java.
