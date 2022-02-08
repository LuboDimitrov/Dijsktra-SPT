import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    private final String name; //Node name
    private List<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE; //distance from source (by default all distances are infinite)
    private Map<Node, Integer> adjacentNodes = new HashMap<>(); //Neighbors

    public Node(String name) {
        this.name = name;
    }

    public void addDestinationDirected(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public void addDestinationUndirected(Node source, Node destination, int distance) {
        source.addDestinationDirected(destination, distance);
        destination.addDestinationDirected(source, distance);
    }

    public String getName() {
        return name;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

}