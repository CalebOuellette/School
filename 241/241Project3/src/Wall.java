/**
 * Created by Caleb on 5/24/17.
 */
public class Wall {
    public int cellA;
    public int cellB;

    Wall(int A, int B){
        this.cellA = A;
        this.cellB = B;
    }


    public int getSmallCell(){
        if(cellA < cellB){
            return cellA;
        }
        else{
            return cellB;
        }

    }

    public int getBigCell(){
        if(cellA > cellB){
            return cellA;
        }
        else{
            return cellB;
        }

    }

    public boolean isVerticalWall(){
        if(this.getSmallCell() + 1 == this.getBigCell()){
            return true;
        }else{
            return false;
        }

    }

}
