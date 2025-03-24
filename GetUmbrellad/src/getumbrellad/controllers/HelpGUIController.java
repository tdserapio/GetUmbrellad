package getumbrellad.controllers;

import getumbrellad.views.MainMenuGUI;
import getumbrellad.views.HelpGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelpGUIController implements ActionListener, MouseListener {
    
    private HelpGUI helpGUI;
    private JButton menuButton;
    
    public HelpGUIController(HelpGUI helpGUI, JButton menuButton) {
        
        this.helpGUI = helpGUI;
        this.menuButton = menuButton;
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            MainMenuGUI display = new MainMenuGUI();
            display.setVisible(true);
            helpGUI.dispose();
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
