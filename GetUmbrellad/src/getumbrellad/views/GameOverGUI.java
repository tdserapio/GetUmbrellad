package getumbrellad.views;

import getumbrellad.controllers.GameOverGUIController;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class GameOverGUI extends JFrame {

    private JButton menuButton = null;
    private JLabel  gameMessage = null;

    public GameOverGUI(boolean died) {
        
        setTitle(died ? "Game Over" : "Victory!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(0, 20));     // gap = 20 px
        
        
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream is = getClass().getResourceAsStream("../resources/Lexend.ttf");
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, is));
        } catch (Exception anyException) {
            System.out.println(anyException.getMessage());
            return;
        }

        gameMessage = new JLabel(died ? "Game Over" : "Congratulations – You Win!");
        gameMessage.setHorizontalAlignment(SwingConstants.CENTER);
        gameMessage.setFont(new Font("Lexend", Font.BOLD, 45));
        add(gameMessage, BorderLayout.CENTER);

        menuButton = new JButton("Return to Main Menu");
        add(menuButton, BorderLayout.SOUTH);
        
        GameOverGUIController controller = new GameOverGUIController(this, menuButton);
        menuButton.addActionListener(controller);
        
        setSize(900, 600);
        
    }
}
