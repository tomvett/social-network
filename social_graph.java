import java.util.*;

public class social_graph {

    //map is good (fast data access and unique kjey value pairs)
    private HashMap<social_graph_node, ArrayList<social_graph_node>> adjacencyList;

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
    
    //return every node in the graph (all people on the network)
    public Set<social_graph_node> getAllNodes() {
        return adjacencyList.keySet();
    }
    
    //made this to debug and ensure the graph is being created properly
    public void printGraph() {
        for (social_graph_node node : adjacencyList.keySet()) {
            List<social_graph_node> followees = adjacencyList.get(node);
            System.out.print(node + " follows: "); //this implicitly call toString thats being overriden in the node class
            for (social_graph_node followee : followees) {
                System.out.print(followee + " ");
            }
            System.out.println(); //makes a new line
        }
    }

    public double calculateDensity() {
        int numVertices = adjacencyList.size();
        if (numVertices == 0) {
            return 0.0; //need to return 0 here incase there are no nodes
        }

        int numEdges = 0;
        for (ArrayList<social_graph_node> nodes : adjacencyList.values()) {
            numEdges += nodes.size(); //get the num edges
        }

        //find the max num of edges
        int maxEdges = numVertices * (numVertices - 1);

        double density = (double)numEdges / maxEdges;
        return density;
    }

    private social_graph_node compareAlphabeticallyTwoNodes(social_graph_node node1, social_graph_node node2) {
        //definetly can improve this and make it look nicer but it does the job for now
        if (node1 == null && node2 == null) {
            return null;
        }
        else if (node1 == null) {
            return node2;
        }
        else if (node2 == null) {
            return node1;
        }

        int result = node1.getName().compareToIgnoreCase(node2.getName());

        if (result < 0) {
            return node1;
        }
        else if (result > 0) {
            return node2;
        }
        return null;
    }

    private ArrayList<social_graph_node> getAllConnectedNodes (social_graph_node nodeIn) {
        ArrayList<social_graph_node> nodes = new ArrayList<>();
        //loop through every node in the graph and check to see if its in input node's adjacency list (following)
        for (social_graph_node L_node : adjacencyList.keySet()) {
            ArrayList<social_graph_node>  connectedNodes = adjacencyList.get(L_node);
            if (connectedNodes.contains(nodeIn)) {
                nodes.add(L_node);
            }
        }
        return nodes;
    }

    public social_graph_node getNodeWithMostEdgesTo() {
        int edgeCount = 0;
        social_graph_node returnNode = null;
        //loop through each node, get the number of edges TO from each node and compare that each time
        for (social_graph_node node : adjacencyList.keySet()) {
            int size = getAllConnectedNodes(node).size();
            if (size == edgeCount) {
                returnNode = compareAlphabeticallyTwoNodes(returnNode, node);
            }
            else if (size > edgeCount) {
                returnNode = node;
                edgeCount = size;
            }
        }
        return returnNode;
    }

    public social_graph_node getNodeWithMostEdgesAway() {
        int edgeCount = 0;
        social_graph_node returnNode = null;
        //loop through each node, get the number of edges AWAY from each node and compare that each time
        for (social_graph_node node : adjacencyList.keySet()) {
            int size = adjacencyList.get(node).size();
            if (size == edgeCount) {
                returnNode = compareAlphabeticallyTwoNodes(returnNode, node);
            }
            else if (size > edgeCount) {
                returnNode = node;
                edgeCount = size;
            }
        }
        return returnNode;
    }

}
