package getumbrellad.controllers;
    
import getumbrellad.models.exceptions.Player;
import getumbrellad.views.HelpGUI;
import getumbrellad.views.InventoryGUI;
import getumbrellad.views.LevelGameplayGUI;
import getumbrellad.views.PauseGUI;
import getumbrellad.views.MainMenuGUI;
import getumbrellad.views.StoreMenuGUI;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.JButton;
    
public class PauseGUIController implements ActionListener, MouseListener, KeyListener{

    private PauseGUI frame;
    private JButton menuButton, inventoryButton, helpButton, resumeButton;
    private LevelGameplayGUI levelGameplayGUI;
    private Player currentPlayer;

    public PauseGUIController(PauseGUI frame, JButton menuButton, JButton inventoryButton, JButton helpButton, JButton resumeButton, LevelGameplayGUI levelGameplayGUI, Player currentPlayer) {
        this.frame = frame;
        this.menuButton = menuButton;
        this.inventoryButton = inventoryButton;
        this.helpButton = helpButton;
        this.resumeButton = resumeButton;
        this.levelGameplayGUI = levelGameplayGUI;
        this.currentPlayer = currentPlayer;
    }

    public void redirect(ActionEvent e) {
        if (e.getSource() == menuButton) {
            menuButton.setBackground(null);
            MainMenuGUI display = new MainMenuGUI();
            display.setVisible(true);
            frame.dispose();
        }
        if (e.getSource() == inventoryButton) {
            inventoryButton.setBackground(null);
            InventoryGUI display = new InventoryGUI(frame, currentPlayer);
            display.setVisible(true);
            frame.dispose();
        }
        if (e.getSource() == helpButton) {
            helpButton.setBackground(null);
            HelpGUI display = new HelpGUI(this.frame);
            display.setVisible(true);
            frame.dispose();
        }
        if (e.getSource() == resumeButton) {
            resumeButton.setBackground(null);
            levelGameplayGUI.getController().togglePause();
            levelGameplayGUI.setVisible(true);
            frame.dispose();
        }
    }

    public void changeColor(MouseEvent e) {
        if (e.getSource() == menuButton) {
            menuButton.setBackground(Color.LIGHT_GRAY);
        }
        if (e.getSource() == inventoryButton) {
            inventoryButton.setBackground(Color.LIGHT_GRAY);
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
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            levelGameplayGUI.getController().togglePause();
            levelGameplayGUI.setVisible(true);
            frame.dispose();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    
   
    
}
