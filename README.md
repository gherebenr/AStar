# AStar
Implementation of the A* pathfinding algorithm. 

### Getting Started
1) Run: ```javac *.java```. This will compile everything.
2) Run any of the following:
    1) ```java Main AStar``` - This will run A* on a random map.
    2) ```java Main AStar -seed NUM``` - Replace NUM with a number. Will run A* on a map with a specific seed.
    3) ```java Main AStar -load MTAFT.XYZ``` - Will run A* on the Mt. St. Helens map.
    4) ```java Main AStar Dijkstra``` - You can run multiple pathfinding algorithms for comparison. (Dijkstra will take a lot longer than A*. Just be patient.)

### More Details
1) The cost is 2<sup>h2-h1</sup> to move by one node. h1 is the height of the current node, and h2 is the height of the target node.
2) The red on the map represents all the explored nodes, and the blue represents the chosen path.

## I only wrote AStar.java and Dijkstra.java.
