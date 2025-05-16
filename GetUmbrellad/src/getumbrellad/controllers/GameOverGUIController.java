package getumbrellad.controllers;

import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;
import getumbrellad.views.MainMenuGUI;
import getumbrellad.views.GameOverGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverGUIController implements ActionListener, MouseListener {
    
    private GameOverGUI gameOver;
    private JButton menuButton;
    
    public GameOverGUIController(GameOverGUI gameOver, JButton menuButton) {
        this.gameOver = gameOver;
        this.menuButton = menuButton;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            
            var display = new MainMenuGUI();
            display.setVisible(true);
            gameOver.dispose();
            
            Player currentPlayer = new Player("Umbrella Boy", 100, 10, 0, 1);
            try {
                currentPlayer.writePlayer("umbrella_boy.csv");
            } catch (PlayerNotFoundException pnfe) {
                System.err.println("Sorry, player cannot be reset successfully.");
                return;
            }
            
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == menuButton) {
            menuButton.setBackground(Color.gray);
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == menuButton) {
            menuButton.setBackground(null);
        }
    }
    
}

