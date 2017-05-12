import java.util.ArrayList;

/**
 * Created by Caleb on 5/11/17.
 * Tree to hold spheres.
 */
public class QTreeNode {

    public QTreeNode(double width, double height, double topPixel, double leftPixel){
        this.width = width;
        this.height = height;
        this.topPixel = topPixel;
        this.leftPixel = leftPixel;
    }

    private double width;
    private double height;
    private double topPixel;
    private double leftPixel;

    private QTreeNode TLChild;
    private QTreeNode TRChild;
    private QTreeNode BLChild;
    private QTreeNode BRChild;

    private boolean hasChildren = false;
    private int depth = 0;

    public ArrayList<mySphere> sphereArray = new ArrayList<>();


    public void createChildren() {
        this.depth++;
        if (hasChildren) {
            this.TLChild.createChildren();
            this.TRChild.createChildren();
            this.BLChild.createChildren();
            this.BLChild.createChildren();
        } else {
            hasChildren = true;
            this.TLChild = new QTreeNode(Math.floor(width / 2), Math.floor(height / 2), topPixel, leftPixel);
            this.TRChild = new QTreeNode(Math.ceil(width / 2), Math.floor(height / 2), topPixel, leftPixel + Math.floor(width / 2));
            this.BLChild = new QTreeNode(Math.floor(width / 2), Math.ceil(height / 2), topPixel - Math.floor(height / 2), leftPixel);
            this.BLChild = new QTreeNode(Math.ceil(width / 2), Math.ceil(height / 2), topPixel - Math.floor(height / 2), leftPixel + Math.floor(width / 2));
        }
    }


    public void insertSphere(mySphere s){

        //if sphere is inserted
        if(hasChildren){
            this.TLChild.insertSphere(s);
            this.TRChild.insertSphere(s);
            this.BLChild.insertSphere(s);
            this.BLChild.insertSphere(s);
        }
    }


}
