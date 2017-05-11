import javafx.geometry.Point3D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Caleb on 5/10/2017.
 */
public class World {

    public Color backgroundColor;
    public ArrayList<mySphere> sphereArray = new ArrayList<mySphere>();
    public Camera camera;
    public double scl; //scale
    public double width = 512;
    public double height = 512;

    //method for reading in file.
    World(double cameraDistance, Color background, double _scale){
        this.camera = new Camera(cameraDistance);
        this.backgroundColor = background;
        this.scl = _scale;
    }

    public void addSphere(mySphere s){
        sphereArray.add(s);
    }

    public Color getColor(double x,double y){
        Point3D pixel = new Point3D((scl *((2 *x)/width -1)), (scl *((2 *y)/height -1)), 0);
        

        return backgroundColor;
    }

    public void drawWorld(){
        Drawer e = new Drawer(this);
    }


}
