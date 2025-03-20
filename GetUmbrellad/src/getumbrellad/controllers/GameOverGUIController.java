package getumbrellad.controllers;

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

