import java.util.HashSet;
import java.util.Set;
import java.util.Random;
/**
 * Created by Caleb on 5/17/17.
 */
public class Maze {


    private int rows;
    private int columns;

    private Set<Set> CellSet = new HashSet<Set>();
    private Set<Set> adjacentCells = new HashSet<Set>();

    public Maze(int rows, int columns){
        this.rows = rows;
        this.columns = columns;

        int cellIndex = 0;

        // In the beginning, we regard each cell as in its own set by itself
        //(since it is separated from the others by walls). Thus, we have a partition of the set of n cells into n
        //sets.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; i < columns; i++) {
                Set<Integer> cell = new HashSet<Integer>();
                cell.add(cellIndex);
                CellSet.add(cell);
                cellIndex++;
            }
        }

        for(int x = 0; x < (rows * columns); x = x+1){
            Set<Set> adj  = getAdjacent(x);
            adjacentCells.addAll(adj);
        }
    }



    public int selectRandomCell(){
        Random rand = new Random();
        int  n = rand.nextInt((rows * columns));
        return n;
    }

    public Set<Set> getAdjacent(int n){
        Set<Set> adjacentList = new HashSet<Set>();

        //up
        if(n > (columns - 1)){
            Set<Integer> up = new HashSet<Integer>();
            up.add(n);
            up.add(n - columns);
            adjacentList.add(up);
        }

        //down
        if(n < (rows * columns) - columns){
            Set<Integer> dn = new HashSet<Integer>();
            dn.add(n);
            dn.add(n + columns);
            adjacentList.add(dn);
        }
        //left
        if(n % columns != 0){
            Set<Integer> l = new HashSet<Integer>();
            l.add(n);
            l.add(n-1);
            adjacentList.add(l);
        }
        //right
        if((n +1) % columns != 0){
            Set<Integer> r = new HashSet<Integer>();
            r.add(n);
            r.add(n + 1);
            adjacentList.add(r);
        }

        return adjacentList;
    }



}