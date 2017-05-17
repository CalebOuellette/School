import javafx.geometry.Point3D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Caleb on 5/10/2017.
 * Holds data for creating a world and all elements in it. All executes the drawing of the world.
 */
public class World {

    private Color backgroundColor;
    private ArrayList<mySphere> sphereArray = new ArrayList<>();
    private Camera camera;
    private double scl; //scale
    private LambertianLight light;

    public double pixelsTested = 0;
    private QTreeNode tree;


    World( Color background, double _scale){
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


    private Color getColorBoundingBox(double x,double y) {
        //used for testing bounding boxes.
        Point3D pixel = new Point3D((scl *((2 *x)/Constants.WIDTH -1)),(scl *((2 *y)/Constants.HEIGHT -1)), 0);
        ArrayList<mySphere> sl = tree.getSpheresByLocation(pixel);

        if(sl.size() > 0 ){

            return sl.get(0).color;

        }else{
            return backgroundColor;
        }
    }

    protected Color getColor(double x,double y){
        //main function to get a color for each pixel

        Point3D pixel = new Point3D((scl *((2 *x)/Constants.WIDTH -1)),(scl *((2 *y)/Constants.HEIGHT -1)), 0); //calculate pixel on image plane.

        ArrayList<mySphere> sl = tree.getSpheresByLocation(pixel); //get list of sphere from quad tree.

        Double bestDistance = null; //the shortest distance found
        mySphere bestSphere = null; //the sphere of shorted distance
        Point3D intersect = null; //the intersection of the sphere and the ray.
        for (mySphere aSphere : sl) {
            //for each sphere calculate the intersection, note that if there are no spheres in the this part of the tree we don't enter this code
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

        if(bestDistance != null){//if we found an intersection, get the color
            return getShadedColor(intersect, bestSphere); //apply shading
        }else{
            return backgroundColor; //return background
        }
    }


    private double calcDistance(Point3D v, Point3D q, double r){
        //calculates the distance between the camera and sphere. returns the lower value.
        double a = v.dotProduct(v);
        double b = q.dotProduct(v) * 2;
        double c = q.dotProduct(q) - (r*r);

        double root1, root2, d, returnRoot;

        d = b * b - 4 * a * c;

        root1 = ( - b + Math.sqrt(d))/(2*a);
        root2 = (-b - Math.sqrt(d))/(2*a);

        returnRoot = root1 > root2 ? root2: root1; //check what root is lower.

        return returnRoot;
    }


    public Color getShadedColor(Point3D point, mySphere s){
        //calculates the shaded color using the sphere, intersection and light.
        if(this.light == null){ //if no light no need to shade.
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
        if(camera == null){
            System.out.println("Error drawing: No Camera Found");
            return;
        }


        for (mySphere aSphere : sphereArray) {
            //create bounding boxes
            BoundingBox b = new BoundingBox(camera, aSphere);
            aSphere.box = b;
        }

        //build quad tree
        this.tree = new QTreeNode(scl * 2, scl * 2, -1 * (scl *((2)/Constants.WIDTH -1)),  (scl *((2)/Constants.HEIGHT -1)));
        for (int i = 0; i < Constants.treeHeight; i++) {
            this.tree.createChildren();
        }

        //insert into quad tree each sphere
        for (mySphere aSphere : sphereArray) {
            this.tree.insertSphere(aSphere);
        }

        //start the drawing process
        Drawer e = new Drawer(this);
    }



}
