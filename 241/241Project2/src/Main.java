import java.awt.*;

public class Main {

    public static void main(String[] args) {
        LambertianLight l = new LambertianLight(0.577, 0.577, 0.577);
        World w = new World(-20, new Color(0.4f, 1.0f, 1.0f), 10, l);

        mySphere r = new mySphere(2.0, 2.0, -1.0, 3, new Color(1.0f, 0.0f, 0.0f));
        w.addSphere(r);
  //      mySphere g = new mySphere(2, -2, 0, 3, new Color(0.0f, 1.0f, 0.0f));
        //w.addSphere(g);
//        mySphere b = new mySphere(-2, 0, -1.0, 3, new Color(0.0f, 0.0f, 1.0f));
 //       w.addSphere(b);
        w.drawWorld();

    }
}
