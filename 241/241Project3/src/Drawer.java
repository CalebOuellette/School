
/**
 * Drawer illustrates some basics of Java 2D.
 * This version is compliant with Java 1.2 Beta 3, Jun 1998.
 * Please refer to: <BR>
 * http://www.javaworld.com/javaworld/jw-07-1998/jw-07-media.html
 * <P>
 * @author Bill Day <bill.day@javaworld.com>
 * @version 1.0
 * @see java.awt.Graphics2D
 **/

/**
 Geoffrey Matthews modified this code to show how to make
 an image pixel by pixel.
 13 April 2017
 **/

import java.awt.*;
import java.awt.event.*;

public class Drawer extends Frame {


    private Maze maze;

    /**
     * Our Drawer constructor sets the frame's size, adds the
     * visual components, and then makes them visible to the user.
     * It uses an adapter class to deal with the user closing
     * the frame.
     **/
    public Drawer(Maze w) {

        //Title our frame.
        super("Java 2D Drawer");

        this.maze = w;
        //Set the size for the frame.
        setSize(512, 512);

        //We need to turn on the visibility of our frame
        //by setting the Visible parameter to true.
        setVisible(true);

        //Now, we want to be sure we properly dispose of resources
        //this frame is using when the window is closed.  We use
        //an anonymous inner class adapter for this.
        addWindowListener(new WindowAdapter()
                          {public void windowClosing(WindowEvent e)
                          {dispose(); System.exit(0);}
                          }
        );
    }

    /**
     * The paint method provides the real magic.  Here we
     * cast the Graphics object to Graphics2D to illustrate
     * that we may use the same old graphics capabilities with
     * Graphics2D that we are used to using with Graphics.
     **/
    public void paint(Graphics g) {
        int scale = (512 - 10) / (maze.columns + 1);
        int pixelOffsetY = 40;
        int pixelOffsetX = 30;


        for (Wall wall : maze.walls) {
            if(wall.isVerticalWall()){
                int x = wall.getSmallCell() % maze.columns;
                int y = wall.getSmallCell() / maze.columns;
                x = x + 1;
                g.setColor(new Color(0,0,0));

                g.drawLine(pixelOffsetX + (scale * x),pixelOffsetY + (scale * y), pixelOffsetX + (scale * x), pixelOffsetY + (scale * (y + 1)));
            }else{
                int x = wall.getSmallCell() % maze.columns;
                int y = wall.getSmallCell() / maze.columns;

                y = y + 1;
                g.setColor(new Color(0,0,0));
                g.drawLine(pixelOffsetX + (scale * x), pixelOffsetY +  (scale * y), pixelOffsetX + (scale * (x + 1)), pixelOffsetY + (scale * y));
            }
        }

        g.drawLine(pixelOffsetX + (1 * scale),pixelOffsetY +0, pixelOffsetX + (scale *  (maze.columns - 1)),pixelOffsetY + 0);
        g.drawLine(pixelOffsetX + 0,pixelOffsetY + 0,pixelOffsetX + 0,pixelOffsetY + (scale *  maze.rows));
        g.drawLine(pixelOffsetX + (scale *  maze.columns),pixelOffsetY + 0,pixelOffsetX + (scale *  maze.columns),pixelOffsetY + (scale *  (maze.rows - 1)));
        g.drawLine(pixelOffsetX + 0, pixelOffsetY + (scale *  maze.columns),pixelOffsetX + (scale *  maze.columns), pixelOffsetY +(scale *  maze.rows));
        //Draw Outer walls
    }





}
