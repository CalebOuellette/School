import java.util.*;

public class Maze {

  private int[][] mazeData;
  private int[][] solutionData;
  private int[][] solution;
  private int height;
  private int width;

  Maze(int[][] mazeData, int height, int width) {
    System.out.println(Arrays.deepToString(mazeData));
    this.mazeData = mazeData;
    this.height = height;
    this.width = width;
    this.solutionData = new int[height][width];
    this.solution = new int[height][width];
    solution[height - 1][width - 1] = 1;
  }

  public int[][] solve() {
    buildSolutionMatrix();
    backTraceSolutions(width - 1, height - 1);
    System.out.println(Arrays.deepToString(this.solutionData));
    System.out.println(Arrays.deepToString(this.solution));
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

  private void backTraceSolutions(int x, int y) {
    // find best move
    if (x == 0 && y == 0) {
      solution[x][y] = 1;
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

    for (int i = 0; i < possilbeMoves.size(); i++) {
      Pair p = possilbeMoves.get(i);
      if(p.value == bestOptions) {
        solution[p.y][p.x] = 1;
        backTraceSolutions(p.x, p.y);
      }
    }
  }


}