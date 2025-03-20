package getumbrellad.controllers;

import getumbrellad.views.MainMenuGUI;
import getumbrellad.views.StoreMenuGUI;
import getumbrellad.views.LevelGameplayGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LevelGameplayGUIController implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
    
    private boolean umbrellaOpen = true;
    private double angle, distanceX, distanceY;
    private int movementX, movementY, movementFactor = 3;
    private LevelGameplayGUI levelGameplay; 
    private JButton menuButton, storeButton;
    
    
    public LevelGameplayGUIController(LevelGameplayGUI screen, JButton menu, JButton store) {
        
        levelGameplay = screen;
        menuButton = menu;
        storeButton = store;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            var display = new MainMenuGUI();
            levelGameplay.dispose();
            display.setVisible(true);
        }
        if (e.getSource() == storeButton) {
            var display = new StoreMenuGUI();
            levelGameplay.dispose();
            display.setVisible(true);
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
        if (e.getSource() == storeButton) {
            storeButton.setBackground(Color.gray);
        }
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == menuButton) {
            menuButton.setBackground(null);
        }
        if (e.getSource() == storeButton) {
            storeButton.setBackground(null);
        }
        
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        try {
            Thread.sleep(100L);
        
            double x = e.getX();
            double y = e.getY();
            //System.out.println("Mouse is at grid (" + x + ", " + y + ")");

            JPanel[] panel = levelGameplay.getGridPanels();
            distanceX = (levelGameplay.getPlayerX() + 0.5) / levelGameplay.getGridRatioX() - x;
            distanceY = (levelGameplay.getGridColumn() - (levelGameplay.getPlayerY() + 10.0)) / levelGameplay.getGridRatioY() - y;
            //grid column - y position because y positive is initially inverted
            
            angle = Math.atan(distanceX/distanceY) * 180 / Math.PI + 90;
            //System.out.println("Mouse angle is " + angle);
            
            int umbrellaX = -1;
            int umbrellaY = 1;
            
            if (angle < 0) {
                umbrellaX = -1;
                umbrellaY = 1;
            }
            else if (angle < 25) {
                umbrellaX = 1;
                umbrellaY = 1;
            }
            else if (angle < 75) {
                umbrellaX = 1;
                umbrellaY = 2;
            }
            else if (angle < 105) {
                umbrellaX = 0;
                umbrellaY = 2;
            }
            else if (angle < 155) {
                umbrellaX = -1;
                umbrellaY = 2;
            }
            else if (angle < 180) {
                umbrellaX = -1;
                umbrellaY = 1;
            }
            
            umbrellaX += levelGameplay.getPlayerX();
            umbrellaY += levelGameplay.getPlayerY();
            levelGameplay.setUmbrella(umbrellaX, umbrellaY);
            
            //System.out.println("Umbrella is at grid (" + umbrellaX + ", " + umbrellaY + ")");
            
            //end of try
        }
        catch (Exception error) {
            System.out.println(error);
        }
    }

    
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
            umbrellaOpen = !umbrellaOpen;
            
            if (umbrellaOpen) { // slow the fall
                //insert code for delaying the fall
            }
            else { // move the player
                //double totalDistance = distanceX + distanceY;
                movementX = (int) Math.round(movementFactor * (Math.cos(angle / 180 * Math.PI)));
                movementY = (int) Math.round(movementFactor * (Math.sin(angle / 180 * Math.PI)));
                
                
                levelGameplay.movePlayer(movementX, movementY);
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        
        if (e.getKeyChar() == 'a') {
            levelGameplay.movePlayer(-1, 0);
            //find a delay or method that somehow ignores spam inputs or doesnt allow spam inputs
        }
        if (e.getKeyChar() == 'd') {
            levelGameplay.movePlayer(1, 0);
            //find a delay or method that somehow ignores spam inputs or doesnt allow spam inputs
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {}

}