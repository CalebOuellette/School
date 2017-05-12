import javafx.geometry.Point3D;

import java.util.ArrayList;

/**
 * Created by Caleb on 5/11/17.
 * Tree to hold spheres.
 * Each Qtree represents a portion of the XY plane.
 */
public class QTreeNode {



    private double width; //the width of the section represented
    private double height; //the height of the section represented
    private double topPixel; //the top most pixel of the section represented
    private double leftPixel; //the most left pixel of the section represented

    private QTreeNode TLChild; //the Top Left child Qtree
    private QTreeNode TRChild; //Top Right
    private QTreeNode BLChild;
    private QTreeNode BRChild;

    private boolean hasChildren = false; //flag to check if the children have been created.
    private int depth = 0; //how tall this tree is.

    public ArrayList<mySphere> sphereArray = new ArrayList<>();

    public QTreeNode(double width, double height, double topPixel, double leftPixel){
        this.width = width;
        this.height = height;
        this.topPixel = topPixel;
        this.leftPixel = leftPixel;
    }

    public void createChildren() {
        this.depth++; //we are adding children so the tree's size increased one layer.
        if (hasChildren) { // if this node has children already add the children to it's children
            this.TLChild.createChildren();
            this.TRChild.createChildren();
            this.BLChild.createChildren();
            this.BRChild.createChildren();
        } else {
            //if it does not have children, create the children by dividing this node's section of screen.
            hasChildren = true;
            this.TLChild = new QTreeNode(Math.floor(width / 2), Math.floor(height / 2), topPixel, leftPixel);
            this.TRChild = new QTreeNode(Math.ceil(width / 2), Math.floor(height / 2), topPixel, leftPixel + Math.floor(width / 2));
            this.BLChild = new QTreeNode(Math.floor(width / 2), Math.ceil(height / 2), topPixel - Math.floor(height / 2), leftPixel);
            this.BRChild = new QTreeNode(Math.ceil(width / 2), Math.ceil(height / 2), topPixel - Math.floor(height / 2), leftPixel + Math.floor(width / 2));
        }
    }


    public void insertSphere(mySphere s){
        //insert a sphere based on it's bounding box. If it intersects add it.
        if((s.box.x2 >= leftPixel  && s.box.x1 <= leftPixel + width) && (s.box.y2 >= topPixel - height && s.box.y1 <= topPixel )){ //
            this.sphereArray.add(s);
            if(hasChildren){
                //if has children add the sphere down.
                this.TLChild.insertSphere(s);
                this.TRChild.insertSphere(s);
                this.BLChild.insertSphere(s);
                this.BRChild.insertSphere(s);
            }
        }
    }

    public ArrayList<mySphere> getSpheresByLocation(Point3D pixel){
        //takes a pixel and returns the sphere list from the lowest child.
        ArrayList<mySphere> out = new ArrayList<>();
        if(pixel.getY() <= topPixel && pixel.getY() >= topPixel - height && pixel.getX() <= leftPixel + width && pixel.getX() >= leftPixel){
            if(!hasChildren){
                out = this.sphereArray;
            }else{
                out.addAll(this.TLChild.getSpheresByLocation(pixel));
                out.addAll(this.TRChild.getSpheresByLocation(pixel));
                out.addAll(this.BLChild.getSpheresByLocation(pixel));
                out.addAll(this.BRChild.getSpheresByLocation(pixel));
            }
        }
        return out;
    }
}
