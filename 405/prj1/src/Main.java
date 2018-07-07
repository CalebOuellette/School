import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {

    if(args.length != 1){
      return;
    }

    try {
      InputResult maze=  readInput(args[0]);
      Maze m = new Maze(maze.maze, maze.height, maze.width);
      int[][] solution = m.solve();
      String x = writeOutput(maze.maze, solution, maze.height, maze.width);
      System.out.print(x);
    } catch (Exception e){
      return;
    }

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

  private static class InputResult{
    int[][] maze;
    int height;
    int width;
  }


  public static InputResult readInput(String path) throws Exception{
    File file = new File(path);
    BufferedReader br = new BufferedReader(new FileReader(file));

    String st;
    int height = 0;
    int width = 0;
    List<List<Integer>> maze = new ArrayList<List<Integer>>();
    while ((st = br.readLine()) != null){
      List<Integer> line = parseLine(st);
      if(line.size() != 0){
        height++;
        width = line.size();
      }
      maze.add(line);
    }
    int[][] output = new int[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        output[y][x] = maze.get(y).get(x);
      }
    }
    InputResult o = new InputResult();
    o.height = height;
    o.width = width;
    o.maze = output;
    return o;
  }

  public static List<Integer> parseLine(String input){
    String[] strArray = input.split(" ");
    List<Integer>  intArray =  new ArrayList<Integer>();
    for(int i = 0; i < strArray.length; i++) {
      if(!strArray[i].equals("")){
        intArray.add( Integer.parseInt(strArray[i]));
      }
    }
    return intArray;
  }

}