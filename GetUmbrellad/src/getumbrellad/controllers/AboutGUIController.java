package getumbrellad.controllers;

import getumbrellad.views.MainMenuGUI;
import getumbrellad.views.AboutGUI;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;

public class AboutGUIController implements ActionListener, MouseListener {
    
    private JFrame AboutGUI;
    private JButton menuButton;
    

    public AboutGUIController(JFrame AboutGUI, JButton menuButton){
        this.AboutGUI = AboutGUI;
        this.menuButton = menuButton;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainMenuGUI menu = new MainMenuGUI();
        menu.setVisible(true);
        AboutGUI.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        menuButton.setBackground(Color.GRAY);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        menuButton.setBackground(new JButton().getBackground());
    }
}
