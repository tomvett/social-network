import java.util.*;

public class social_graph {

    //map is good (fast data access and unique key value pairs)
    //the map stores a node and the nodes it follows
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
        for (social_graph_node L_node : adjacencyList.keySet()) {
            List<social_graph_node> nodes = adjacencyList.get(L_node);
            System.out.print(L_node + ": "); //this implicitly calls toString thats being overriden in the node class
            for (social_graph_node L_node2 : nodes) {
                System.out.print(L_node2 + " ");
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

    public ArrayList<social_graph_node> findNodesAtTwoDeg(social_graph_node nodeIn) {
        ArrayList<social_graph_node> result = new ArrayList<>();

        //get the followers of the main node
        ArrayList<social_graph_node> connectedNodes = getAllConnectedNodes(nodeIn);
        for (social_graph_node L_node : connectedNodes) {
            //then get each of those followers followers
            ArrayList<social_graph_node> connectedNodes2 = getAllConnectedNodes(L_node);
            for (social_graph_node L_node2 : connectedNodes2) {
                //then check that those followers arent the nodeIn and arent in the nodeIn's followers and haven't already been visited
                if (!L_node2.equals(nodeIn) && !connectedNodes.contains(L_node2) && !result.contains(L_node2)) {
                    System.out.println(L_node2);
                    result.add(L_node2);
                }
            }
        }
        return result;
    }

    public double calculateMedianConnections() {
        ArrayList<Integer> connectionCounts = new ArrayList<>();

        //count connections from each node
        for (social_graph_node node : adjacencyList.keySet()) {
            int numConnections = getAllConnectedNodes(node).size();
            connectionCounts.add(numConnections);
        }

        //perform a sort (apparently its merge sort)
        Collections.sort(connectionCounts);

        //then just find the midpoint and calculate midpoint
        int size = connectionCounts.size();
        //safety first
        if (size == 0) {
            return 0.0;
        }

        if (size % 2 == 1) {
            //odd number of elements so just get the middle value
            int index = size / 2;
            return connectionCounts.get(index);
        } else {
            //even number of elements so need to do some maths with two elements in the middle(mid1 and mid2)
            int mid1 = size / 2 - 1;
            int mid2 = size / 2;
            return (connectionCounts.get(mid1) + connectionCounts.get(mid2)) / 2.0;
        }
    }

    public social_graph_node findPersonWithMaxReach() {
        //this map will monitor 
        HashMap<social_graph_node, ArrayList<social_graph_node>> reachabilityMap = new HashMap<>();
        for (social_graph_node node : adjacencyList.keySet()) {
            reachabilityMap.put(node, new ArrayList<>());
        }

        // Explore reachability for each person using DFS
        for (social_graph_node node : adjacencyList.keySet()) {
            dfs(node, reachabilityMap);
        }

        // Find the person with the maximum reach
        social_graph_node nodeWithMaxReach = null;
        int maxReach = 0;

        for (social_graph_node node : reachabilityMap.keySet()) {
            int reachSize = reachabilityMap.get(node).size();
            System.out.println(node + ": " + reachSize);
            if (reachSize > maxReach) {
                maxReach = reachSize;
                nodeWithMaxReach = node;
            }
        }

        return nodeWithMaxReach;
    }

    //objects are passed by reference in java, so this function does NOT need a return type and can just modify the map
    private void dfs(social_graph_node current, HashMap<social_graph_node, ArrayList<social_graph_node>> reachabilityMap) {
        Stack<social_graph_node> stack = new Stack<>();
        Set<social_graph_node> visited = new HashSet<>();

        stack.push(current);

        while (!stack.isEmpty()) {
            social_graph_node node = stack.pop();

            if (!visited.contains(node)) {
                visited.add(node);
                reachabilityMap.get(current).add(node);

                for (social_graph_node neighbor : adjacencyList.get(node)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }
}
