import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class file_handler {

    //using an arraylist here because I don't know the how big the file is so I can't define an array of set size
    private ArrayList<String> lines;

    public file_handler(String filePath) {
        //no type given to the array list as it isn't needed afaik
        lines = new ArrayList<>();

        try {
            File file = new File(filePath);

            //explains itself
            if (!file.exists()) {
                System.out.println("File doesn't exist");
                return;
            }

            Scanner scanner = new Scanner(file);

            //reads each line from the file and adds it to the arraylist
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line); // Add each line to the ArrayList
            }

            // Close the Scanner
            scanner.close();

        } 
        catch (FileNotFoundException e) {
            //just handling the most common error, although I don't think this will ever happen as file is being passed via cmd line
            System.out.println("Error:" + e.getMessage());
        }
    }

    public ArrayList<String> getLines() {
        return lines;
    }

}
