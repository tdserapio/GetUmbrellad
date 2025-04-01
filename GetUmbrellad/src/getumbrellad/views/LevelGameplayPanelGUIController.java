/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appliedlevelgameplaytesting;
import java.awt.event.*;


/**
 *
 * @author bruv
 */
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

