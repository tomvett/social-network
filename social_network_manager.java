import java.util.ArrayList;

public class social_network_manager {

    public static void main(String[] args) {
        //this is where I control my social network and the flow of the program

        
        String filePath = "path/to/your/file.txt"; //TESTING FILE PATH
        //open the file containing the data
        file_handler fileHandler = new file_handler(filePath);
        ArrayList<String> fileLines = fileHandler.getLines();
        for (String line : fileLines) { //debug
            System.out.println(line);
        }


        


    }
    
}
