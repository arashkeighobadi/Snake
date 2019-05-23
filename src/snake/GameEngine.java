package snake;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author Arash
 */
public class GameEngine extends JPanel {
    
    private final int FPS = 5;
    private final int SNAKE_MOVEMENT = 20;
    
    private boolean paused = false;
    private Image background;
    private int levelNum = 1;
    private int score = 0;
    private Level level;
    private Snake snake;
   // private SnakeUnit rock;
    private Timer newFrameTimer;
    
    public GameEngine() {
        super();
        
//       DBConnect connect = new DBConnect();
       //connect.insertData();
//       connect.getData();

        //path to be changed.
        background = new ImageIcon("data/images/background.jpg").getImage();
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed a");
        this.getActionMap().put("pressed a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if((snake.getHead().x - SNAKE_MOVEMENT) != snake.snake.get(1).x){
                    snake.setVelx(-SNAKE_MOVEMENT);
                    snake.setVely(0);
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed d");
        this.getActionMap().put("pressed d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if((snake.getHead().x + SNAKE_MOVEMENT) != snake.snake.get(1).x){
                    snake.setVelx(SNAKE_MOVEMENT);
                    snake.setVely(0);
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed s");
        this.getActionMap().put("pressed s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if((snake.getHead().y + SNAKE_MOVEMENT) != snake.snake.get(1).y){
                    snake.setVelx(0);
                    snake.setVely(SNAKE_MOVEMENT);
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if((snake.getHead().y - SNAKE_MOVEMENT) != snake.snake.get(1).y){
                    snake.setVelx(0);
                    snake.setVely(-SNAKE_MOVEMENT);
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
    }
    

    
    public void restart() {

        Image image = new ImageIcon("data/images/ball.png").getImage();
        snake = new Snake(image);
        
        try {
            level = new Level(snake);
            //System.out.println(level.getFoods().list.size());
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        //food?
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 800, 600, null);
        level.draw(g);
        snake.draw(g);
    }
    
    class NewFrameListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                //snake.move();
                

//                System.out.println(snake.getHead().y);
                if (level.collides(snake)) { //snake collides with rocks
                    System.out.println(JOptionPane.showInputDialog(null, "Player's name: ") + "'s score: " + score);
                    levelNum = 0;
                    score = 0;
                    restart();
                    System.out.println("colides with level");
                }
                for (int i = 1; i<snake.snake.size()-1; i++) { //snake collides with itself
                    if (snake.collides(snake.snake.get(i))) {
                        System.out.println(JOptionPane.showInputDialog(null, "Player's name: ") + "'s score: " + score);
                        levelNum = 0;
                        score = 0;
                        restart();
                        System.out.println("colides with itself");
                    }
                }
                if (level.getFoods().gotEaten(snake)) {
                    //add to player's score!
                    score++;
                    System.out.println("score" + score);
                }
                snake.move();//snake head
                snake.moveR();//the rest of the snake
            }
            if (level.won()) {
                //change level
                if (levelNum > 10) {
                    //End the game. The player has won!
                    System.out.println(JOptionPane.showInputDialog(null, "Player's name: ") + "'s score: " + score);
                    levelNum = 0;
                    restart();
                    System.out.println("levelNum > 10");
                } else {
                    try {
                        level.loadLevel(++levelNum);
                        System.out.println("Level" + levelNum);
                    }
                    catch (Exception ex) {
                        System.out.println("Error in loading level: " + ex);
                    }
                }
                
            }
            repaint();
        }
    }
    
    public Level getLevel() {
        return this.level;
    }
    
}
