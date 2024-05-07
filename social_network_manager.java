import java.util.*;

public class social_network_manager {

    private static HashMap<String, social_graph_node> nameUserMap;

    public static void main(String[] args) {
        //this is where I control my social network and the flow of the program
        //this is probably over the top but id much rather write more code and whack the components into their own clases
        //ive made the assumption everyone has a unique name
        
        String filePath = "C:/Users/Tom/Downloads/test-socialnetworks/test-socialnetworks/social-network1.txt"; ///TESTING FILE PATH

        //init
        file_handler fileHandler = new file_handler(filePath);
        social_graph SocialGraph = new social_graph();
        nameUserMap = new HashMap<>(); //set the map to a value

        //add everyone on the network to the social graph
        for (String name : fileHandler.getUniqueUsers()) {
            social_graph_node user = new social_graph_node(name);
            putNameUser(name, user);
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

        SocialGraph.printGraph(); //debug

        //now I can start on the tasks

    }

    private static void putNameUser(String name, social_graph_node user) {
        nameUserMap.put(name, user);
    }



    
}
