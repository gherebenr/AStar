import java.awt.Point;
import java.util.*;

public class Dijkstra implements AIModule
{
    public List<Point> createPath(final TerrainMap map)
    {
        final Point startNode = map.getStartPoint();
        final Point goalNode = map.getEndPoint();
        Point currentNode = startNode;
        Point childNode = goalNode;

        HashMap<Point, Point> parentMap = new HashMap<Point, Point>();
        HashMap<Point, Double> costToNodeWithoutHeuristic = new HashMap<Point, Double>();

        // Keeps all the nodes that have been visited, ordered by the cost.
        PriorityQueue<Point> fringe = new PriorityQueue<Point>((Point a, Point b)-> (costToNodeWithoutHeuristic.get(a)).compareTo(costToNodeWithoutHeuristic.get(b)));

        // Keeps all the nodes that have been visited. Faster to search than a priority queue.
        HashSet<Point> closedSet = new HashSet<Point>();
        
        List<Point> shortestPath = new ArrayList<Point>();

        double tempCost = 0;
        boolean neighborOnFringe = false;
        
        costToNodeWithoutHeuristic.put(startNode, 0.0);
        closedSet.add(currentNode);

        // While the goal node hasn't been reached, try to find a path to it.
        while (!currentNode.equals(goalNode)) 
        {
            // Checks the eight surrounding nodes of the current node.
            for (Point neighbor : map.getNeighbors(currentNode))
            {
                // If the node was already visited, move on.
                if (closedSet.contains(neighbor)){continue;}

                // The cost to move to the new node from the current node.
                tempCost = costToNodeWithoutHeuristic.get(currentNode) + map.getCost(currentNode, neighbor);

                // Boolean that is true if the neighbor node is in the priority queue.
                neighborOnFringe = fringe.contains(neighbor);

                // Checks if the neighbor has never been visited before, or it has been visited but the new path is cheaper.
                if (!neighborOnFringe || tempCost < costToNodeWithoutHeuristic.get(neighbor))
                {
                    // Set currentNode as the parent of neighbor.
                    parentMap.put(neighbor, currentNode);
                    // Set the costs.
                    costToNodeWithoutHeuristic.put(neighbor, tempCost);
                    // If a cheaper cost was found, remove the node from the priority queue so it can be readded to the proper position.
                    if(neighborOnFringe){fringe.remove(neighbor);}
                    fringe.add(neighbor);
                }
            }
            // The new currentNode is the one with the lowest cost (including heuristic).
            currentNode = fringe.poll();
            // Add the current node to the closed set, because it has been visited.
            closedSet.add(currentNode);
        }
        // Generating the shortest path by going backwards from the goal node to the start node.
        while (parentMap.containsKey(childNode)) 
        {
            shortestPath.add(0,childNode);
            childNode = parentMap.get(childNode);
        }
        shortestPath.add(0,startNode);
        
        return shortestPath;
    }
}
