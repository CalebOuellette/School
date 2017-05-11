import java.awt.*;

public class Main {

    public static void main(String[] args) {
        if(args.length > 0){
            System.out.println("Reading file: " + args[0]);
        }else{
            System.out.println("No world file given, Exiting");
            return;
        }

        if(args.length > 1){
            Constants.scale = Double.parseDouble(args[1]);
        }
        System.out.println("Scale set at: " + Constants.scale);
        if(Constants.scale == 10.0){
            System.out.println("To change scale pass in a 2nd command line argument.");
        }






        LambertianLight l = new LambertianLight(-0.577, 0.577, -0.577);
        World w = new World(20, new Color(0.4f, 1.0f, 1.0f), Constants.scale);

        mySphere r = new mySphere(2.0, 2.0, -1.0, 3, new Color(1.0f, 0.0f, 0.0f));
        w.addSphere(r);
        mySphere g = new mySphere(2, -2, 0, 3, new Color(0.0f, 1.0f, 0.0f));
        w.addSphere(g);
        mySphere b = new mySphere(-2, 0, 1.0, 3, new Color(0.0f, 0.0f, 1.0f));
        w.addSphere(b);
        w.drawWorld();

    }
}
