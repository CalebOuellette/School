import javafx.geometry.Point3D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Caleb on 5/10/2017.
 * Holds data for creating a world and all elements in it.
 */
public class World {

    private Color backgroundColor;
    private ArrayList<mySphere> sphereArray = new ArrayList<>();
    private Camera camera;
    private double scl; //scale
    private LambertianLight light;

    private QTreeNode tree;

    World(){

    }

    World(Camera camera, Color background, double _scale){
        this.camera = camera;
        this.backgroundColor = background;
        this.scl = _scale;
    }

    public void addSphere(mySphere s){
        sphereArray.add(s);
    }

    public void setLight(LambertianLight l){
        this.light  = l;
    }

    public void setCamera(Camera c){
        this.camera  = c;
    }


    public Color getColor(double x,double y) {
        Point3D pixel = new Point3D((scl *((2 *x)/Constants.WIDTH -1)),(scl *((2 *y)/Constants.HEIGHT -1)), 0);
        ArrayList<mySphere> sl = tree.getSpheresByLocation(pixel);

        if(sl.size() > 0 ){

            return sl.get(0).color;

        }else{
            return backgroundColor;
        }
    }

    public Color getColorShade(double x,double y){

        //get list of spheres based on quad tree


        Point3D pixel = new Point3D((scl *((2 *x)/Constants.WIDTH -1)),(scl *((2 *y)/Constants.HEIGHT -1)), 0);

        Double bestDistance = null;
        mySphere bestSphere = null;
        Point3D intersect = null;
        for (mySphere aSphere : sphereArray) {
            Point3D v = pixel.subtract(camera).normalize();

            Point3D q = camera.subtract(aSphere);



            double distance = calcDistance(v, q, aSphere.radius);
            if(!Double.isNaN(distance)){
                if( bestDistance == null){
                    bestDistance = distance;
                    bestSphere = aSphere;
                    intersect = camera.add(v.multiply(bestDistance));
                } else if ( bestDistance > distance){
                    bestDistance = distance;
                    bestSphere = aSphere;
                    intersect = camera.add(v.multiply(bestDistance));
                }
            }
        }

        if(bestDistance != null){

            return getShadedColor(intersect, bestSphere);

        }else{
            return backgroundColor;
        }
    }

    public Color getShadedColor(Point3D point, mySphere s){
        if(this.light == null){
            return s.color;
        }
        Point3D n = s.subtract(point).normalize();
        n = n.normalize();

        float shade = (float) n.dotProduct(this.light);
        if(shade < 0.1f){
            shade = 0.1f;
        }
        Color outColor  = new Color( shade *  s.color.getRed()/ 255f , shade * s.color.getGreen() / 255f, shade * s.color.getBlue() / 255f  );

        return outColor;
    }




    public void drawWorld(){
        //create bounding boxes

        for (mySphere aSphere : sphereArray) {
            BoundingBox b = new BoundingBox(camera, aSphere);
            aSphere.box = b;
        }

        //build quad tree
        this.tree = new QTreeNode(scl * 2, scl * 2, -1 * (scl *((2)/Constants.WIDTH -1)),  (scl *((2)/Constants.HEIGHT -1)));
        for (int i = 0; i < Constants.treeHeight; i++) {
            this.tree.createChildren();
        }


        //insert into quad tree
        for (mySphere aSphere : sphereArray) {
            this.tree.insertSphere(aSphere);
        }

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
