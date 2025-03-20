package getumbrellad.controllers;

import getumbrellad.views.StoreMenuGUI;
import getumbrellad.views.LevelGameplayGUI;
import getumbrellad.models.exceptions.ItemNotFoundException;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;

public class StoreMenuGUIController implements ActionListener, MouseListener {
    
    private JFrame GUI;
    private JButton exitButton;
    private JPanel itemsPanel;
    
    public StoreMenuGUIController(JFrame GUI, JButton exitButton, JPanel itemsPanel) {
        this.GUI = GUI;
        this.exitButton = exitButton;
        this.itemsPanel = itemsPanel;
    }
    
    public void goToMenu(ActionEvent e){
        
        if (e.getSource() == exitButton) {
            
            LevelGameplayGUI levelGameplay = new LevelGameplayGUI();
            levelGameplay.setVisible(true);
            GUI.dispose();
            
        }
        
    }
//    
    public String getItemName(JPanel currentJPanel) throws ItemNotFoundException {
        
        for (Component c: currentJPanel.getComponents()) {
            if (c instanceof JLabel) {
                JLabel foundComponent = (JLabel)(c);
                if (foundComponent.getText() == null) continue;
                return foundComponent.getText();
            }
        }
        throw new ItemNotFoundException("The item name requested cannot be found.");
    }
    
    public void buyItem(MouseEvent e) {
        
        try {
            String itemName = getItemName((JPanel)e.getSource());
            int confirm = JOptionPane.showOptionDialog(
                GUI,
                "Are you sure you want to buy " + itemName + "?",
                "Purchase Confirmation", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null
            );

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, itemName + " successfully purchased!", "OK", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, itemName + " purchase was cancelled.", "OK", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ItemNotFoundException inf) {
            JOptionPane.showMessageDialog(null, "Oops, item not found!", "OK", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        goToMenu(e);
    }

    @Override    
    public void mouseEntered(MouseEvent e){
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        buyItem(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
