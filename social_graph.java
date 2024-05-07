import java.util.*;

public class social_graph {

    //map is good
    private Map<social_graph_node, List<social_graph_node>> adjacencyList;

    public social_graph() {
        adjacencyList = new HashMap<>();
    }
    
    //add a new node to the graph
    public void addNode(social_graph_node node) {
        if (!adjacencyList.containsKey(node)) {
            adjacencyList.put(node, new ArrayList<>());
        }
    }
    
    //add an edge between to nodes
    public void addEdge(social_graph_node fromNode, social_graph_node toNode) {
        //ensure both nodes exist (idk if this is needed, might be easier to add everyone on begin)
        addNode(fromNode);
        addNode(toNode);
        
        //add the edge
        adjacencyList.get(fromNode).add(toNode);
    }
    
    //get the all nodes directly connected to the input nodes (followers)
    public List<social_graph_node> getNeighbours(social_graph_node node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }
    
    //return every node in the graph (all people on the network)
    public Set<social_graph_node> getAllNodes() {
        return adjacencyList.keySet();
    }
    
    //made this to debug and ensure the graph is being created properly
    public void printGraph() {
        for (social_graph_node node : adjacencyList.keySet()) {
            List<social_graph_node> followees = adjacencyList.get(node);
            System.out.print(node + " follows: ");
            for (social_graph_node followee : followees) {
                System.out.print(followee + " ");
            }
            System.out.println(); //makes a new line
        }
    }
}
