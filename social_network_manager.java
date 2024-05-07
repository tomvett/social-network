import java.util.ArrayList;

public class social_network_manager {

    public static void main(String[] args) {
        //this is where I control my social network and the flow of the program
        //this is probably over the top but id much rather write more code and whack the components into their own clases

        
        String filePath = "C:/Users/Tom/Downloads/test-socialnetworks/test-socialnetworks/social-network1.txt"; ///TESTING FILE PATH
        //open the file containing the data
        file_handler fileHandler = new file_handler(filePath);
        ArrayList<String> fileLines = fileHandler.getLines();
        for (String line : fileLines) { ///debug
            System.out.println(line);
        }

        //making the network graph time
        social_graph SocialGraph = new social_graph();





        


    }



    
}
