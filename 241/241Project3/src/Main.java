public class Main {

    public static void main(String[] args) {
      //  Tests.main();

        Maze mazeOne = new Maze(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        mazeOne.mazeRender();
    }
}
