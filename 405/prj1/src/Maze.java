import java.util.*;

public class Maze {

  private int[][] mazeData;
  private int[][] solutionData;
  private ArrayList<int[][]> solution;
  private int height;
  private int width;

  Maze(int[][] mazeData, int height, int width) {
    this.mazeData = mazeData;
    this.height = height;
    this.width = width;
    this.solutionData = new int[height][width];
    this.solution = new ArrayList<int[][]>();
    this.solution.add(new int[height][width]);
    this.solution.get(0)[height - 1][width - 1] = 1;
  }

  public ArrayList<int[][]> solve() {
    buildSolutionMatrix();
    backTraceSolutions(width - 1, height - 1, this.solution.get(0));
    return this.solution;
  }

  private void buildSolutionMatrix() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        findBestOption(x, y);
      }
    }
  }

  private void findBestOption(int x, int y) {

    Integer best = null;
    if (x - 1 >= 0) {
      // from horizontal
      best = solutionData[y][x - 1];
    }
    if (y - 1 >= 0) {
      // from vertical
      if (best == null || best < solutionData[y - 1][x]) {
        best = solutionData[y - 1][x];
      }
    }
    if (y - 1 >= 0 && x - 1 >= 0) {
      // from diag
      if (best == null || best < solutionData[y - 1][x - 1]) {
        best = solutionData[y - 1][x - 1];
      }
    }

    if (best == null) {
      best = 0;
    }
    solutionData[y][x] = mazeData[y][x] + best;
  }

  private class Pair {
    public int value;
    public Moves move;
    public int x;
    public int y;

    Pair(int value, Moves move) {
      this.move = move;
      this.value = value;
    }

  }

  class PairComparator implements Comparator<Pair> {
    @Override
    public int compare(Pair a, Pair b) {
      return a.value > b.value ? -1 : a.value == b.value ? 0 : 1;
    }
  }

  private void backTraceSolutions(int x, int y, int[][] s) {
    // find best move
    int[][] working = cloneMyTwoArray(s);
    if (x == 0 && y == 0) {
      working[x][y] = 1;
      return;
    }

    List<Pair> possilbeMoves = new ArrayList<Pair>();
    if (x - 1 >= 0) {
      // from horizontal
      Pair p = new Pair(solutionData[y][x -1], Moves.horizontal);
      p.x = x - 1;
      p.y = y;
      possilbeMoves.add(p);
    }
    if (y - 1 >= 0) {
      // from vertical
      Pair p = new Pair(solutionData[y - 1][x], Moves.vertical);
      p.x = x;
      p.y = y - 1;
      possilbeMoves.add(p);
    }
    if (y - 1 >= 0 && x - 1 >= 0) {
      // from diag
      Pair p = new Pair(solutionData[y - 1][x - 1], Moves.diagonal);
      p.x = x - 1;
      p.y = y - 1;
      possilbeMoves.add(p);
    }

    possilbeMoves.sort(new PairComparator());


    int bestOptions = possilbeMoves.get(0).value;
    int option = 0;
    for (int i = 0; i < possilbeMoves.size(); i++) {
      Pair p = possilbeMoves.get(i);
      if(p.value == bestOptions) {
        if(option != 0){
          s = cloneMyTwoArray(working);
          this.solution.add(s);
        }
        s[p.y][p.x] = 1;
        backTraceSolutions(p.x, p.y, s);
        option++;
      }
    }
  }

  private int[][] cloneMyTwoArray(int[][] in){
    int[][] n = new int[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        n[y][x] = in[y][x];
      }
    }
    return n;
  }


}