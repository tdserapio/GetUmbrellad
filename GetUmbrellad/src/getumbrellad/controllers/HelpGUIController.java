package getumbrellad.controllers;

import getumbrellad.views.MainMenuGUI;
import getumbrellad.views.HelpGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class HelpGUIController implements ActionListener, MouseListener, KeyListener {
    
    private HelpGUI helpGUI;
    private JButton menuButton;
    private boolean isAPressed;
    private boolean isDPressed;
    private boolean isSpacePressed;
    private ArrayList<JLabel> imgs;
    
    public HelpGUIController(HelpGUI helpGUI, JButton menuButton, ArrayList<JLabel> imgs) {
        
        this.helpGUI = helpGUI;
        this.menuButton = menuButton;
        this.imgs = imgs;
        
    }
    
    private void changeImage() {
        if (isAPressed && isDPressed) {
            this.imgs.get(0).setIcon(new ImageIcon(getClass().getResource("../resources/arrowkeysAD.png")));
        } else if (isAPressed) {
            this.imgs.get(0).setIcon(new ImageIcon(getClass().getResource("../resources/arrowkeysA.png")));
        } else if (isDPressed) {
            this.imgs.get(0).setIcon(new ImageIcon(getClass().getResource("../resources/arrowkeysD.png")));
        } else {
            this.imgs.get(0).setIcon(new ImageIcon(getClass().getResource("../resources/arrowkeys.png")));
        }
        
        if (isSpacePressed) {
            this.imgs.get(1).setIcon(new ImageIcon(getClass().getResource("../resources/spacebar1.png")));
        } else {
            this.imgs.get(1).setIcon(new ImageIcon(getClass().getResource("../resources/spacebar.png")));
        }
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
    
    @Override
    public void keyPressed(KeyEvent e) {
        int pressedCharacter = e.getKeyCode();
        if (pressedCharacter == KeyEvent.VK_A) {
            isAPressed = true;
        }
        if (pressedCharacter == KeyEvent.VK_D) {
            isDPressed = true;
        }
        if (pressedCharacter == KeyEvent.VK_SPACE) {
            isSpacePressed = true;
        }
        changeImage();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int pressedCharacter = e.getKeyCode();
        if (pressedCharacter == KeyEvent.VK_A) {
            isAPressed = false;
        }
        if (pressedCharacter == KeyEvent.VK_D) {
            isDPressed = false;
        }
        if (pressedCharacter == KeyEvent.VK_SPACE) {
            isSpacePressed = false;
        }
        changeImage();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    
}
