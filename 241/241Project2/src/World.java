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
        Point3D pixel = new Point3D((scl *((2 *x)/width -1)),(scl *((2 *y)/height -1)), 0);

        Double bestDistance = null;
        mySphere bestSphere = null;
        for (mySphere aSphere : sphereArray) {
            Point3D v = pixel.subtract(camera).normalize();

            Point3D q = camera.subtract(aSphere);


            double distance = calcDistance(v, q, aSphere.radius);
            if(!Double.isNaN(distance)){
                if( bestDistance == null){
                    bestDistance = distance;
                    bestSphere = aSphere;
                } else if ( bestDistance > distance){
                    bestDistance = distance;
                    bestSphere = aSphere;
                }
            }
        }

        if(bestDistance != null){
            return bestSphere.color;
        }else{
            return backgroundColor;
        }
    }

    public void drawWorld(){
        Drawer e = new Drawer(this);
    }


    private double calcDistance(Point3D v, Point3D q, double r){

        double a = v.dotProduct(v);
        double b = q.dotProduct(v) * 2;
        double c = q.dotProduct(q) - (r*r);

        double root1, root2, d, returnRoot;

        d = b * b - 4 * a * c;

        root1 = ( - b + Math.sqrt(d))/(2*a);
        root2 = (-b - Math.sqrt(d))/(2*a);

        returnRoot = root1 > root2 ? root2: root1;


        return returnRoot;
    }


}
