package snake;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Arash
 */
public class SnakeGUI {
    private JFrame frame;
    private GameEngine gameArea;
    
    public SnakeGUI() {
        frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        gameArea = new GameEngine();
        frame.getContentPane().add(gameArea);
        
        JMenuBar menuBar = new JMenuBar(); 
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
//        JMenu newMenu = new JMenu("Restart");
//        gameMenu.add(newMenu);
        

        
        JMenuItem exitMenuItem = new JMenuItem("Restart");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) { //when is this name helpful? "ae"
                gameArea.restart();
//                System.exit(0);
            }
        });
        
        frame.setPreferredSize(new Dimension(gameArea.getLevel().getLevelWidth(), gameArea.getLevel().getLevelHeight()));
        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);
        
        
    }
    
}
