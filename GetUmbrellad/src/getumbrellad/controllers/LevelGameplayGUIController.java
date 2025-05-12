package getumbrellad.controllers;

import getumbrellad.models.exceptions.Player;
import getumbrellad.views.LevelGameplayGUI;
import java.awt.event.*;

public class LevelGameplayGUIController implements ActionListener, MouseListener, MouseMotionListener, KeyListener{

    private LevelGameplayGUI panel;
    private Player player;
    
    public LevelGameplayGUIController(LevelGameplayGUI panel, Player player) {
        this.panel = panel;
        this.player = player;
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
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            player.setKeyLeft(true);
        }
        if (e.getKeyChar() == 'd') {
            player.setKeyRight(true);
        }
        if (e.getKeyChar() == ' ') {
            player.setKeyUp(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            player.setKeyLeft(false);
        }
        if (e.getKeyChar() == 'd') {
            player.setKeyRight(false);
        }
        
        //remove space bar jumping later
        if (e.getKeyChar() == ' ') {
            player.setKeyUp(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            panel.togglePause();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.setXMouse(e.getX());
        player.setYMouse(e.getY());
    }

    @Override public void mouseDragged(MouseEvent e) {}

    
}

