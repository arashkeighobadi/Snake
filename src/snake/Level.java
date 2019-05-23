package snake;

import java.util.Random;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.ImageIcon;

/**
 *
 * @author Arash
 */
public class Level {
    
    private final int ROCK_WIDTH = 40;
    //private final int FOOD_WIDTH = 20;
    private final int LEVEL_WIDTH = 800;
    private final int LEVEL_HEIGHT = 600;
    //private static int LevelNo = 0;
    private Foods foods;
    private Snake snake;
    
    ArrayList<Rock> rocks;
//    ArrayList<Food> foods;
    
    public Level(Snake snake) throws IOException {
        //this.snake = new Snake();
        this.snake = snake;
        System.out.println("new level");
        loadLevel(1);//load first level
    }
    
    public void loadLevel(int levelNum) throws FileNotFoundException, IOException {
        int rockNum;
        int foodNum;
        if (levelNum < 5) {
            rockNum = 5;
            foodNum = levelNum + 1;
        } else {
            rockNum = 7;
            foodNum = levelNum + 1;
        }
        
        rocks = new ArrayList<>();
        foods = new Foods();
        
        //Random positioning of rocks and foods.
        Image rockImage = new ImageIcon("data/images/brick03.png").getImage();
        createRocks(rockNum, rockImage);
        
        Image foodImage = new ImageIcon("data/images/brick02.png").getImage();
        createFoods(foodNum, foodImage);
        
    }
    
    public void createRocks(int number, Image rockImage) {
        Random rand = new Random();
        int rX;
        int rY;
        
        for (int i=0; i<number; i++) {
            boolean intersects = false;
            
            do { //make sure it's not too close to the center
                rX = rand.nextInt(LEVEL_WIDTH- ROCK_WIDTH + 1);
            } while (
                rX < (LEVEL_WIDTH/2 + ROCK_WIDTH+5) && rX > (LEVEL_WIDTH/2 - ROCK_WIDTH-5)
            );
            
            do { //make sure it's not too close to the center
                rY = rand.nextInt(LEVEL_HEIGHT- ROCK_WIDTH + 1);
            } while (
                rY < (LEVEL_HEIGHT/2 + ROCK_WIDTH+5) && rY > (LEVEL_HEIGHT/2 - ROCK_WIDTH-5)
            );
            
            //new rock must not be on another rock!
            Rock newRock = new Rock(rX, rY, ROCK_WIDTH, ROCK_WIDTH, rockImage);
            for(Rock rock: rocks) {
                if(newRock.collides(rock)) { //we need to generate this rock again in another position
                    i--; 
                    intersects = true;
                    break;
                }    
            }
            
            //new rock must not be on the snake
            for(SnakeUnit snakeUnit: snake.getSnake()) {
                if(newRock.collides(snakeUnit)) {
                    i--;
                    intersects = true;
                    break;
                }
            }
            
            if (!intersects)
                rocks.add(newRock);
        }
    }
    
    public void createFoods(int number, Image foodImage) {
        Random rand = new Random();
        int fX;
        int fY;
        int FOOD_WIDTH = foods.getFoodWidth();
        
        for (int i=0; i<number; i++) {
            boolean intersects = false;
            
            do { //make sure it's not too close to the center
                fX = rand.nextInt(LEVEL_WIDTH- ROCK_WIDTH + 1);
            } while (
                fX < (LEVEL_WIDTH/2 + FOOD_WIDTH+5) && fX > (LEVEL_WIDTH/2 - FOOD_WIDTH-5)
            );
            
            do { //make sure it's not too close to the center
                fY = rand.nextInt(LEVEL_HEIGHT- FOOD_WIDTH + 1);
            } while (
                fY < (LEVEL_HEIGHT/2 + FOOD_WIDTH+5) && fY > (LEVEL_HEIGHT/2 - FOOD_WIDTH-5)
            );
            
            //new food must not be on another rock!
            Food newFood = new Food(fX, fY, FOOD_WIDTH, FOOD_WIDTH, foodImage);
            for(Rock rock: rocks) {
                if(newFood.collides(rock)) { //we need to generate this food again in another position
                    i--; 
                    intersects = true;
                    break;
                }    
            }
            if (!intersects)
                foods.addFood(newFood);
        }
        
    }
    

    
    public boolean collides(Snake snake) {
        if ((snake.getHead().getX() >= this.LEVEL_WIDTH-snake.getWidth() && snake.getVelx()>0) || 
            (snake.getHead().getX() <= 0 &&  snake.getVelx() < 0) ||
            (snake.getHead().getY() >= this.LEVEL_HEIGHT-snake.getWidth() && snake.getVely() > 0)  ||
            (snake.getHead().getY() <= 0 && snake.getVely() < 0)
            ) {
            //System.out.println("heyo");
            return true;
        }
        
        for (Rock rock : rocks) {
            if (snake.collides(rock)) {
                return true;
            }
        }
        return false;
    }
    

        //instead of isOver
        public boolean won() {
            return foods.list.isEmpty();
        }
        
        public void draw(Graphics g) {
            for (Rock rock : rocks) {
                rock.draw(g);
            }
            for (Food food : foods.list) {
                food.draw(g);
            }
        }
        
        public Foods getFoods() {
            return foods;
        }
        
        public int getLevelWidth() {
            return this.LEVEL_WIDTH;
        }
        public int getLevelHeight() {
            return this.LEVEL_HEIGHT;
        }
        
        
        //draw food?
    
}
