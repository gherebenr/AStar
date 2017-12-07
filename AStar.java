import java.awt.Point;
import java.util.*;

public class AStar implements AIModule
{
    // Function that returns the heuristic used by A*.
    private double getHeuristic(final TerrainMap map, final Point pt1, final Point pt2)
    {
        // If both tiles have the same height, the heuristic is the Chebyshev distance.
        double heuristic = Math.max(Math.abs(pt1.x - pt2.x), Math.abs(pt1.y - pt2.y));
        double pt1Height = map.getTile(pt1);
        double pt2Height = map.getTile(pt2);
        double heightDif = pt2Height - pt1Height;
        // Change the heuristic if the tiles have different height.
        if(heightDif < 0)
        {
            // It costs less to go downhill.
            return heuristic + heightDif * 0.5;
        }
        if(heightDif > 0)
        {
            // It costs more to go uphill.
            return heuristic + heightDif;
        }
        return heuristic;
    }
    
    public List<Point> createPath(final TerrainMap map)
    {
        final Point startNode = map.getStartPoint();
        final Point goalNode = map.getEndPoint();
        Point currentNode = startNode;
        Point childNode = goalNode;

        HashMap<Point, Point> parentMap = new HashMap<Point, Point>();
        HashMap<Point, Double> costToNodeWithoutHeuristic = new HashMap<Point, Double>();
        HashMap<Point, Double> costToNodeWithHeuristic = new HashMap<Point, Double>();

        // Keeps all the nodes that have been visited, ordered by the cost.
        PriorityQueue<Point> fringe = new PriorityQueue<Point>((Point a, Point b)-> (costToNodeWithHeuristic.get(a)).compareTo(costToNodeWithHeuristic.get(b)));

        // Keeps all the nodes that have been visited. Faster to search than a priority queue.
        HashSet<Point> closedSet = new HashSet<Point>();
        
        List<Point> shortestPath = new ArrayList<Point>();

        double tempCost = 0;
        boolean neighborOnFringe = false;
        
        costToNodeWithoutHeuristic.put(startNode, 0.0);
        costToNodeWithHeuristic.put(startNode, getHeuristic(map, startNode, goalNode));
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
                    costToNodeWithHeuristic.put(neighbor, tempCost + getHeuristic(map, neighbor, goalNode));
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
