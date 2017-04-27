import java.io.*;
import java.net.URL;

/**
 * Created by Caleb on 4/15/17.
 */
public class DecodeFile {


    public DecodeFile(){


    }


    public String readfile(String fileName){


        // The name of the file to open.

        // This will reference one line at a time
        String line = null;
        String outText = ";";



        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                outText = outText + line;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
        return outText;
    }

}
