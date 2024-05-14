import java.util.*;

public class analysis {

    private static HashMap<String, social_graph_node> nameUserMap;

    public static void main(String[] args) {
        /*
        -this is where I control my social network and the flow of the program
        -this is probably over the top but id much rather write more code and whack the components into their own clases
        -ive made the assumption everyone has a unique name
        -i have tried to keep the social_graph and social_graph_node class as generic as possible (to just be like a normal graph)
        -i just finished and realised that its more efficient to have two edges between each node to show followers and following, but a bit late now
        -this passes all tests!
         */
        
        //String filePath = "C:/Users/Tom/Downloads/test-socialnetworks/test-socialnetworks/social-network4.txt"; ///TESTING FILE PATH

        //ensure that a file was passed to the program
        if (args.length == 0) {
            System.err.println("No file passed to the program");
            System.exit(1);
        }

        //init
        file_handler fileHandler = new file_handler(args[0]);
        //file_handler fileHandler = new file_handler(filePath); //TESTING FILE PATH
        social_graph SocialGraph = new social_graph();
        nameUserMap = new HashMap<>(); //set the map to a value

        //add everyone on the network to the social graph
        for (String name : fileHandler.getUniqueUsers()) {
            social_graph_node user = new social_graph_node(name);
            putNameUser(name, user); //adds a new user to the hashmap that links the names (strings) to the nodes
            SocialGraph.addNode(user);
        }

        //add the edges to the graph
        for (String line : fileHandler.getLines()) {
            String mainName = fileHandler.getFirstWord(line);
            social_graph_node mainNode = nameUserMap.get(mainName);
            for (String name : fileHandler.getNonFirstWords(line)) {
                SocialGraph.addEdge(mainNode, nameUserMap.get(name));
            }
        }

        // SocialGraph.printGraph(); //debug

        //task1
        task1(SocialGraph);

        //task2
        task2(SocialGraph);

        //task3
        task3(SocialGraph);

        //task4
        task4(SocialGraph, fileHandler);

        //task5
        task5(SocialGraph);

        //task6
        task6(SocialGraph);

    }

    private static void putNameUser(String name, social_graph_node user) {
        nameUserMap.put(name, user);
    }

    private static void task1(social_graph SocialGraph) {
        double density = SocialGraph.calculateDensity();
        System.out.println("1) The density of the graph is: " + density);
    }

    private static void task2(social_graph SocialGraph) {
        social_graph_node node = SocialGraph.getNodeWithMostEdgesTo();
        System.out.println("2) The person with the most followers is: " + node);
    }
    
    private static void task3(social_graph SocialGraph) {
        social_graph_node node = SocialGraph.getNodeWithMostEdgesAway();
        System.out.println("3) The person who follows the most people is: " + node);
    }

    private static void task4(social_graph SocialGraph, file_handler fileHandler) {
        //get the name of the first input person and then get their associated node on the graph
        String FirstPerson = fileHandler.getFirstWord(fileHandler.getLines().get(0));
        social_graph_node firstNode = nameUserMap.get(FirstPerson);

        int numNodes = SocialGraph.findNodesAtTwoDeg(firstNode).size();
        System.out.println("4) The number of people at two degrees of seperation from the first person is: " + numNodes);
    }

    private static void task5(social_graph SocialGraph) {
        double midpoint = SocialGraph.calculateMedianConnections();
        System.out.println("5) The median value for the number of followers is: " + midpoint);
    }

    private static void task6(social_graph SocialGraph) {
        social_graph_node nodeWithMostReach = SocialGraph.findPersonWithMaxReach();

        System.out.println("6) The person with the most influence is: " + nodeWithMostReach);
    }
    
}
