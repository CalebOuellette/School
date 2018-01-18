
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

        if(args.length > 2){
            Constants.HEIGHT = Integer.parseInt(args[2]);
            Constants.WIDTH = Integer.parseInt(args[2]);
        }
        System.out.println("Size set at: " + Constants.HEIGHT + " Pixels");
        if(Constants.HEIGHT == 512){
            System.out.println("To change canvas size pass in a 3rd command line argument.");
        }

        WorldFileAdapter wfa = new WorldFileAdapter();

        World myw = wfa.createWorldFromFile(args[0]);

        myw.drawWorld();


    }
}
