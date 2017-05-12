import javafx.geometry.Point3D;

/**
 * Created by Caleb on 5/11/17.
 */
public class BoundingBox {

    public double x1;
    public double x2;
    public double y1;
    public double y2;


    BoundingBox(Point3D camera, mySphere sphere){

        double a = Math.sqrt((sphere.getY()*sphere.getY()) + Math.pow((sphere.getZ() - camera.getZ()), 2));

        double O = Math.atan(sphere.radius / a);

        double V = Math.asin(sphere.getY() / a);

        double O2 = V - O;

        y1 = camera.getZ() * Math.tan(O2);
        y2 = camera.getZ() * Math.tan(O2 + (2* O));


        double ax = Math.sqrt((sphere.getX()*sphere.getX()) + Math.pow((sphere.getZ() - camera.getZ()), 2));


        double Vx = Math.asin(sphere.getX() / a);

        double O2x = Vx - O;

        x1 = camera.getZ() * Math.tan(O2x);
        x2 = camera.getZ() * Math.tan(O2x + (2* O));
    }





}
