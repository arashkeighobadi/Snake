package snake;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Arash
 */
public class Snake {
    
    private final int SNAKE_WIDTH = 20;
    private boolean wallCollided;
    private Image image;
    //velocity
    private int velx;
    private int vely;
    private SnakeUnit head;
    ArrayList<SnakeUnit> snake;
    //ArrayList<Point> path;
    
    public Snake(Image image) {
        System.out.println("New Snake");
        wallCollided = false;
        this.image = image;
        //Randomly set velx or vely to 1 or -1. the other one which wasn't chosen must be set to zero.
        //@temporary
        changeDirection('x', 20); //(char axis, int vel)
        
        snake = new ArrayList<>();
        
        
        head = new SnakeUnit(/*1,*/ 400, 300, SNAKE_WIDTH, SNAKE_WIDTH, image);
        
        snake.add(head);
        
        //path = new ArrayList<>();
        SnakeUnit secondUnit = new SnakeUnit(/*2,*/ 400, 300 - SNAKE_WIDTH, SNAKE_WIDTH, SNAKE_WIDTH, image);
        snake.add(secondUnit);
    }
    
    //probably needs to be changed. make it more optimized. game shows one food at a time.
    //so no need for the loop.

    
    public boolean collides(Sprite otherSprite) {
        return head.collides(otherSprite);
    }
    
    public void move() {
        int x = head.getX();
        int y = head.getY();
        
        Point point = new Point(x, y);
        head.setPreLoc(point);
        
        if ((velx<0 && x>0) || (velx>0 && x+SNAKE_WIDTH<800)){ //keeps moving horizontaly
            snake.get(0).setX(x + velx);
        }
    
        if ((vely<0 && y>0) || (vely>0 && y+SNAKE_WIDTH<600)){ //keeps moving vertically
            snake.get(0).setY(y + vely);
        }
    }
        
    public void moveR() {
    //each snakeUnit will move to the previous pos of the next unit.
    //the head is already moved hence index from 1.
        for(int i=1; i<snake.size(); i++ ){
            Point tmp = new Point(snake.get(i).getX(), snake.get(i).getY());
//            tmp.x = snake.get(i).getX();
//            tmp.y = snake.get(i).getY();
            snake.get(i).setPreLoc(tmp);

            Point tmp2 = new Point(snake.get(i-1).getPreLoc().x, snake.get(i-1).getPreLoc().y);            
//            tmp2 = snake.get(i-1).getPreLoc();
            snake.get(i).setX(tmp2.x);
            snake.get(i).setY(tmp2.y);
//            System.out.println("next" + snake.get(1).getX());
        }
    }
    
    public void grow() {
        int L = snake.size()-1; //lentgh of the snake
        SnakeUnit newUnit = new SnakeUnit(snake.get(L).getPreLoc().x, snake.get(L).getPreLoc().y,
        SNAKE_WIDTH, SNAKE_WIDTH, this.image);
        snake.add(newUnit);
    }
    

    
    public void changeDirection(char axis, int vel) {
        switch(axis) {
            case 'x' :
                velx = vel;
                vely = 0;
                break;
            case 'y' :
                velx = 0;
                vely = vel;
                break;
            default :
                System.out.println("Error in changeDirection.");
                break;
        }
    }
    
    public void draw(Graphics g) {
        for (SnakeUnit unit: snake) {
            unit.draw(g);
        }
    }
    
    public SnakeUnit getHead() {
        return head;
    }
    
    public void setVelx(int velx) {
        this.velx = velx;
    }
    
    public void setVely(int vely) {
        this.vely = vely;
    }
    
    public boolean getWallCollided() { //Has it collided to a wall?
        return wallCollided;
    }
    
    public int getWidth() {
        return this.SNAKE_WIDTH;
    }
    
    public int getVelx() {
        return this.velx;
    }
    public int getVely() {
        return this.vely;
    }
    
   public ArrayList<SnakeUnit> getSnake() {
       return snake;
   }
}
