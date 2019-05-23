package snake;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Arash
 */
public class SnakeUnit extends Sprite {
    
    //private double velx;
    //private double vely;
    private Point preLoc;
//    private int position; //in the snake. head being the first.
    //ArrayList<Point> path;
    
    
    public SnakeUnit (/*int position,*/ int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
        this.preLoc = new Point(x, y);
//        this.position = position;
    }
    
    public Point getPreLoc() {
        return preLoc;
    }
    
    public void setPreLoc(Point point) {
        preLoc = point;
    }
    
}
