import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for reading input from file. You may want to expand it, if needed...
 * 
 * @author  Tone Berg 
 * @version 2015-10-21
 */
public class InputReader
{

    /**
     * Constructor for objects of class InputReader
     */
    public InputReader()
    {
    }

    /**
     * Return all the words in a file.
     * 
     * @param    filename the name of the file
     * @return   an arraylist of all the words in the file
     */
    public ArrayList<String> getWordsInFile(String filename)
    {
        ArrayList<String> words = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename),
                        "8859_1"));
            String line = in.readLine();
            while(line != null) {
                String [] elements = line.split(" ");
                for(int i = 0 ; i < elements.length ; i++){
                    words.add(elements[i]);
                }
                line = in.readLine();
            }
            in.close();
        }
        catch(IOException exc) {
            System.out.println("Error reading words in file: " + exc);
        }
        return words;
    }

}

