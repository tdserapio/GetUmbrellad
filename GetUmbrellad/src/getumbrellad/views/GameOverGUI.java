package getumbrellad.views;

import getumbrellad.controllers.GameOverGUIController;

import javax.swing.*;
import java.awt.*;

public class GameOverGUI extends JFrame {

    private final JButton menuButton;
    private final JLabel  gameMessage;

    public GameOverGUI(boolean died) {
        
        setTitle(died ? "Game Over" : "Victory!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(0, 20));     // gap = 20 px

        gameMessage = new JLabel(died ? "Game Over" : "Congratulations – You Win!");
        gameMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(gameMessage, BorderLayout.CENTER);

        menuButton = new JButton("Return to Main Menu");
        add(menuButton, BorderLayout.SOUTH);
        
        GameOverGUIController controller = new GameOverGUIController(this, menuButton);
        menuButton.addActionListener(controller);
        
        setSize(900, 600);
        
    }
}
