import java.io.*;
import java.net.URL;

/**
 * Created by Caleb on 4/15/17.
 */
public class FileManager {


    public FileManager() {


    }


    public String readfile(String fileName) {


        // The name of the file to open.

        // This will reference one line at a time
        String line = null;
        String outText = "";


        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                outText = outText + line;
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
        return outText;
    }


    public void decodeFileTwo(String inputFile, String outputFile, String codeWord){

        // The name of the file to open.

        // This will reference one line at a time
        String line = null;
        String outText = "";


        try {

            File file = new File(Constants.outPutFile);

            // creates the file
            file.createNewFile();

            // creates a FileWriter Object
            PrintWriter  writer = new PrintWriter (file);


            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(inputFile);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            int offset = 0;
            while((line = bufferedReader.readLine()) != null) {
                outText = outText + line;
                String decodedline = Cipher.decipherString(line, codeWord, offset);
                offset = Cipher.characterInString(line) + offset;
                writer.println(decodedline);
            }

            // Always close files.
            bufferedReader.close();
            writer.flush();
            writer.close();

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            inputFile + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + inputFile + "'");
        }
    }


    }
