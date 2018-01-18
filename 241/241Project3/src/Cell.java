/**
 * Created by Caleb on 5/24/17.
 */
public class Cell {


    private Cell parent;
    public int value;

    Cell(int value){
        this.value = value;
    }

    public Cell getRoot(){
        if(this.parent != null) {
            return parent.getRoot();
        }else{
            return this;
        }
    }


    public void setParent(Cell newParent){
        if(this.parent == null){
            this.parent = newParent;
        }else{
           //Error
        }
    }




}
