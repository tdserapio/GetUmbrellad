package getumbrellad.controllers;

import getumbrellad.views.LevelGameplayPanelGUI;
import java.awt.event.*;

public class LevelGameplayPanelGUIController implements ActionListener, MouseListener, MouseMotionListener, KeyListener{

    LevelGameplayPanelGUI panel;
    
    public LevelGameplayPanelGUIController(LevelGameplayPanelGUI panel) {
        this.panel = panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {}

    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        panel.findMouseDirection(e);
    }

    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        panel.keyPressed(e);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        panel.keyReleased(e);
        panel.setPlayerJumpToggle();
    }
    
}

