package getumbrellad.views;

import getumbrellad.controllers.GameOverGUIController;
import javax.swing.*;
import java.awt.*;

public class GameOverGUI extends JFrame {
    
    private JButton menuButton;
    private JLabel gameMessage;
    private GameOverGUIController controller;
    
    public GameOverGUI(boolean died) {
        
        super();
        String gameOverMessage;
        
        if (died) {
            gameOverMessage = "Game Over";
        }
        else {
            gameOverMessage = "Victory!";
        }
        
        this.setTitle(gameOverMessage);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLayout(new BorderLayout());
               
        menuButton = new JButton("Go To Menu");
        gameMessage = new JLabel();
        gameMessage.setText(gameOverMessage);
        
        this.add(gameMessage);
        this.add(menuButton);
        
        controller = new GameOverGUIController(this, menuButton);
        menuButton.addActionListener(controller);
        
        this.setVisible(true);
        
    }
    
}