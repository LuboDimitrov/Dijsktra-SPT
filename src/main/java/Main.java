import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) {

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        /*
            Adjacency Matrix

            a   b   c   d   e   f
        a   0   2   1   7   -   -
        b   2   0   2   3   -   -
        c   1   2   0   5   1   -
        d   7   3   5   0   2   3
        e   -   -   1   2   0   4
        f   -   -   -   3   4   0
         */

        nodeA.addDestinationUndirected(nodeA, nodeB, 2);
        nodeA.addDestinationUndirected(nodeA, nodeC, 1);
        nodeA.addDestinationUndirected(nodeA, nodeD, 7);

        nodeB.addDestinationUndirected(nodeB, nodeC, 2);
        nodeB.addDestinationUndirected(nodeB, nodeD, 3);

        nodeC.addDestinationUndirected(nodeC, nodeD, 5);
        nodeC.addDestinationUndirected(nodeC, nodeE, 1);

        nodeD.addDestinationUndirected(nodeD, nodeE, 2);
        nodeD.addDestinationUndirected(nodeD, nodeF, 3);

        nodeE.addDestinationUndirected(nodeE, nodeF, 4);


        calculateShortestPathFromSource(nodeA);
    }

    private static Node getClosestNode(Set<Node> unvisitedNodes){
        Node closestOne = null;
        int lowestDistance = Integer.MAX_VALUE;
        for(Node n: unvisitedNodes){
            int distanceToNode = n.getDistance();
            if(distanceToNode < lowestDistance){
                lowestDistance = distanceToNode;
                closestOne = n;
            }
        }
        return closestOne;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath;
            shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public static void calculateShortestPathFromSource(Node source) {
        //REMINDER: Insertion, deletion and retrieval in hashsets are O(1) operations, in hashtrees are O(log n)
        //Hashsets use hash table whereas hashtrees use BR trees
        Set<Node> VisitedNodes = new HashSet<>();
        Set<Node> UnvisitedNodes = new HashSet<>();

        UnvisitedNodes.add(source);
        source.setDistance(0);

        while (UnvisitedNodes.size() != 0) {
            Node currentNode = getClosestNode(UnvisitedNodes);
            UnvisitedNodes.remove(currentNode);
            for (Map.Entry< Node, Integer> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!VisitedNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    UnvisitedNodes.add(adjacentNode);
                }
            }
            VisitedNodes.add(currentNode);
        }


        List<Node> sortedList = VisitedNodes.stream()
                    .sorted(Comparator.comparing(
                        Node::getName)) //comparator
                    .collect(Collectors.toList()); //collector
        System.out.println("------ Source Node: " +source.getName()+ " ------");
        sortedList.forEach(PrintDistance);
        System.out.println("---------------------------");
    }

    static Consumer<Node> PrintDistance =
            node -> System.out.print("Shortest distance to "+ node.getName() +" is "+node.getDistance()+"\n");
}
