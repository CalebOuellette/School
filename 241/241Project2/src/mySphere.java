import javafx.geometry.Point3D;
import java.awt.*;

/**
 * Created by Caleb on 5/10/2017.
 * class to hold information about a sphere. Extend Point3D for operations involving coordinates.
 */
public class mySphere extends Point3D {


    public double radius;

    public Color color;


    mySphere(double x, double y, double z, double _radius, Color color){
        super(x, y, z);
        this.radius = _radius;
        this.color = color;
    }


}
