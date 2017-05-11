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


    private WorldFileAdapter(){

    }

    public World createWorldFromFile(String fileName){

        String line = null;
        String outText = "";

        Camera camera = null;

        World w = new World(20, Constants.background, Constants.scale);

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                if(line.startsWith("camera")){

                }
                else if(line.startsWith("light")){
                    LambertianLight l = new LambertianLight(-0.577, 0.577, -0.577);
                    w.setLight(l);
                }
                else if(line.startsWith("sphere")){
                    mySphere r = new mySphere(2.0, 2.0, -1.0, 3, new Color(1.0f, 0.0f, 0.0f));
                    w.addSphere(r);
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

        return "";
    }




}
