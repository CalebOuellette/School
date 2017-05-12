import javafx.geometry.Point3D;

/**
 * Created by Caleb on 5/10/2017.
 * Represents a light source
 */
public class LambertianLight extends Point3D {


    LambertianLight(double x, double y, double z){
        super(-1 * x, y, -1 * z);

    }


}
