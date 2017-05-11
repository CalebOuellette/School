import java.awt.*;

public class Main {

    public static void main(String[] args) {
        World w = new World(20, new Color(1, 1, 1), 1);

        mySphere s = new mySphere(0, 0, 0, .5, new Color(0.0f, 1.0f, 0.0f));

        w.addSphere(s);
        w.drawWorld();

    }
}
