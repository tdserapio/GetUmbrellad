package getumbrellad.controllers;
    
import getumbrellad.views.HelpGUI;
import getumbrellad.views.InventoryGUI;
import getumbrellad.views.LevelGameplayGUI;
import getumbrellad.views.MainMenuGUI;
import getumbrellad.views.StoreMenuGUI;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.JButton;
    
public class LevelGameplayGUIController implements ActionListener, MouseListener, MouseMotionListener, KeyListener{

    private LevelGameplayGUI frame;
    private JButton menuButton, inventoryButton, storeButton, helpButton, resumeButton;

    public LevelGameplayGUIController(LevelGameplayGUI frame, JButton menuButton, JButton inventoryButton, JButton storeButton, JButton helpButton, JButton resumeButton) {
        this.frame = frame;
        this.menuButton = menuButton;
        this.inventoryButton = inventoryButton;
        this.storeButton = storeButton;
        this.helpButton = helpButton;
        this.resumeButton = resumeButton;
    }

    public void redirect(ActionEvent e) {
        if (e.getSource() == menuButton) {
            MainMenuGUI display = new MainMenuGUI();
            display.setVisible(true);
            frame.dispose();
        }
        if (e.getSource() == inventoryButton) {
            InventoryGUI display = new InventoryGUI(frame);
            display.setVisible(true);
            frame.dispose();
        }
        if (e.getSource() == storeButton) {
            StoreMenuGUI display = new StoreMenuGUI(frame);
            display.setVisible(true);
            frame.dispose();
        }
        if (e.getSource() == helpButton) {
            HelpGUI display = new HelpGUI(this.frame);
            display.setVisible(true);
            frame.dispose();
        }
        if (e.getSource() == resumeButton) {
            frame.pauseGame();
        }
    }

    public void changeColor(MouseEvent e) {
        if (e.getSource() == menuButton) {
            menuButton.setBackground(Color.LIGHT_GRAY);
        }
        if (e.getSource() == inventoryButton) {
            inventoryButton.setBackground(Color.LIGHT_GRAY);
        }
        if (e.getSource() == storeButton) {
            storeButton.setBackground(Color.LIGHT_GRAY);
        }
        if (e.getSource() == helpButton) {
            helpButton.setBackground(Color.LIGHT_GRAY);
        }
        if (e.getSource() == resumeButton) {
            resumeButton.setBackground(Color.LIGHT_GRAY);
        }
    }

    public void resetColor(MouseEvent e) {
        if (e.getSource() == menuButton) {
            menuButton.setBackground(null);
        }
        if (e.getSource() == inventoryButton) {
            inventoryButton.setBackground(null);
        }
        if (e.getSource() == storeButton) {
            storeButton.setBackground(null);
        }
        if (e.getSource() == helpButton) {
            helpButton.setBackground(null);
        }
        if (e.getSource() == resumeButton) {
            resumeButton.setBackground(null);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        redirect(e);
    }

    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {
        changeColor(e);
    }
    @Override
    public void mouseExited(MouseEvent e) {
        resetColor(e);
    }
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}

    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    
}

