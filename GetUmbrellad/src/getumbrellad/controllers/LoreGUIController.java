package getumbrellad.controllers;

import getumbrellad.views.MainMenuGUI;
import getumbrellad.views.LoreGUI;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoreGUIController implements ActionListener, MouseListener {
    
    private JFrame LoreGUI;
    private JButton menuButton;
    

    public LoreGUIController(JFrame LoreGUI, JButton menuButton){
        this.LoreGUI = LoreGUI;
        this.menuButton = menuButton;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainMenuGUI menu = new MainMenuGUI();
        menu.setVisible(true);
        LoreGUI.dispose();
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
        try {
            menuButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/mainmenu_darken.png"))));
        } catch (IOException ioe) {
            System.out.println("Error while loading button.");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        try {
            menuButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/mainmenu.png"))));
        } catch (IOException ioe) {
            System.out.println("Error while loading button.");
        }
    }
}
