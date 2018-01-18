import java.util.*;

/**
 * Created by Caleb on 5/17/17.
 */
public class Maze {


    public int rows;
    public int columns;


    public Map<Integer, Cell> cellMap = new HashMap<Integer, Cell>();
    public List<Wall> walls = new ArrayList<Wall>();

    public Maze(int rows, int columns){
        this.rows = rows;
        this.columns = columns;

        int cellIndex = 0;

        // In the beginning, we regard each cell as in its own set by itself
        //(since it is separated from the others by walls). Thus, we have a partition of the set of n cells into n
        //sets.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell x = new Cell(cellIndex);
                cellMap.put(cellIndex, x);
                cellIndex++;
            }
        }

        for(int x = 0; x < (rows * columns); x = x+1){
            List<Wall> adj  = getAdjacent(x);
            walls.addAll(adj);
        }
        this.randomizeMaze();

        this.buildMaze();
    }




    public int selectRandomCell(){
        Random rand = new Random();
        int  n = rand.nextInt((rows * columns));
        return n;
    }

    public List<Wall> getAdjacent(int n){
        List<Wall> adjacentList = new ArrayList<Wall>();


        //down
        if(n < (rows * columns) - columns){
            Wall dn = new Wall(n, n + columns);
            adjacentList.add(dn);
        }

        //right
        if((n +1) % columns != 0){
            Wall r = new Wall(n, n + 1);
            adjacentList.add(r);
        }

        return adjacentList;
    }



    public void buildMaze(){
        //Removes wall to create maze
        List<Wall> doorCells = new ArrayList<Wall>();

        for (Wall testWall : this.walls) {
            Cell ACell = cellMap.get(testWall.cellA);
            Cell BCell =  cellMap.get(testWall.cellB);
            if(ACell.getRoot() != BCell.getRoot()){
                doorCells.add(testWall); //add wall to doors
                cellMap.get(testWall.cellA).getRoot().setParent(cellMap.get(testWall.cellB)); //set parent
            }
        }

        this.walls.removeAll(doorCells);


    }

    public void randomizeMaze(){

        for (int i = 0; i < this.walls.size(); i++) {
            //get random
            int r = selectRandomCell();
            //swap this with random
            Wall tempWall = this.walls.get(i);
            this.walls.set(i, this.walls.get(r));
            this.walls.set(r, tempWall);
        }
        
        
        
    }
    
    
    public void mazeRender(){
         new Drawer(this);
    }



    public void getXY(Cell cell){

        int yInt = cell.value / rows;
        int xInt = cell.value % rows;


    }



}