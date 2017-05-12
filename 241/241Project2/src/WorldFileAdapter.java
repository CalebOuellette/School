import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Caleb on 5/11/2017.
 */
public class WorldFileAdapter {


    WorldFileAdapter(){

    }

    public World createWorldFromFile(String fileName){

        String line = null;
        String outText = "";

        Camera camera = null;

        World w = new World( camera, Constants.background, Constants.scale);

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                if(line.startsWith("camera:")){
                    line = removeNextValue(line);

                    double distance = Double.parseDouble(getNextValue(line));
                    w.setCamera(new Camera(distance));
                }
                else if(line.startsWith("light:")){

                    line = removeNextValue(line);

                    double x = Double.parseDouble(getNextValue(line));
                    line = removeNextValue(line);
                    double y = Double.parseDouble(getNextValue(line));
                    line = removeNextValue(line);
                    double z = Double.parseDouble(getNextValue(line));

                    LambertianLight l = new LambertianLight(x, y, z);
                    w.setLight(l);
                }
                else if(line.startsWith("sphere:")){

                    line = removeNextValue(line);

                    double x = Double.parseDouble(getNextValue(line));
                    line = removeNextValue(line);
                    double y = Double.parseDouble(getNextValue(line));
                    line = removeNextValue(line);
                    double z = Double.parseDouble(getNextValue(line));
                    line = removeNextValue(line);
                    double radius = Double.parseDouble(getNextValue(line));
                    line = removeNextValue(line);
                    float r = Float.parseFloat(getNextValue(line));
                    line = removeNextValue(line);
                    float g = Float.parseFloat(getNextValue(line));
                    line = removeNextValue(line);
                    float b = Float.parseFloat(getNextValue(line));


                    mySphere sp = new mySphere(x, y, z, radius, new Color(r, g, b));
                    w.addSphere(sp);
                }
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


        return w;
    }

    private String getNextValue(String string){

        String word = "";
        if(string.contains(" ")){
            word = string.substring(0, string.indexOf(" "));
        }else{
            word = string;
        }
        return word;
    }

    private String removeNextValue(String string){
        String word = "";
        if(string.contains(" ")){
            word = string.substring(string.indexOf(" ") + 1, string.length());
        }else{
            word = string;
        }
        return word;
    }




}
