public class Main {

  public static void main(String[] args) {
    int[][] maze = { { 1, 3, 0 }, { 3, 3, 3 }, { 0, 3, 3 } };
    int[][] mazeTwo = {
            {1, 0, 9, 4, 16, -20, 2},
            {5, 5, 4 ,3,  2, 6, 0},
            {7, 8, 15, 2, 6, 7, 8,},
            {4, 4, 17, 19, 2, 9, 2},
            {5, -6, 7, 2, 2, -2, 2},
            {3 ,2 ,13 ,3 ,9 ,17 ,3},
            {4, 5, 7, 8, 15, 9, 1},
    };

    Maze m = new Maze(mazeTwo, 8, 7);
    int[][] solution = m.solve();
    String x = writeOutput(mazeTwo, solution, 8, 7);
    System.out.print(x);
  }



  public static String writeOutput(int[][] values, int[][] solution,  int height, int width){
    String out = "";

    for (int y = 0; y < height; y++) {
      out += "\n";
      for (int x = 0; x < width; x++) {
        if(solution[y][x] == 1){
          String s = String.valueOf(values[y][x]);
          out +=  String.valueOf(values[y][x]) + "    ".substring(s.length());
        }else{
          out += "    ";
        }
      }
    }
    out += "\n";
    return out;
  }
}