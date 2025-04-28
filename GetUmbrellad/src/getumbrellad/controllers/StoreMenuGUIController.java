package getumbrellad.controllers;

import getumbrellad.views.StoreMenuGUI;
import getumbrellad.views.LevelGameplayGUI;
import getumbrellad.models.exceptions.ItemNotFoundException;
import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;
import getumbrellad.models.exceptions.Upgrade;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreMenuGUIController implements ActionListener, MouseListener {
    
    private JFrame GUI, previousFrame;
    private JButton exitButton;
    private JPanel itemsPanel;
    
    public StoreMenuGUIController(JFrame GUI, JButton exitButton, JPanel itemsPanel, JFrame previousFrame) {
        this.GUI = GUI;
        this.exitButton = exitButton;
        this.itemsPanel = itemsPanel;
        this.previousFrame = previousFrame;
    }
    
    public void goBack(ActionEvent e){
        
        if (e.getSource() == exitButton) {
            
            previousFrame.setVisible(true);
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
        
        Player dummy = new Player();
        Player actualPlayer;
        try {
            actualPlayer = dummy.readPlayer("umbrella_boy.csv");
        } catch (PlayerNotFoundException pnfe) {
            System.out.println("Sorry, cannot buy.");
            return;
        }
        
        try {
            
            String itemName = getItemName((JPanel)e.getSource());
            
            Upgrade actualUpgrade = null;
            
            for (Upgrade currUPG: Upgrade.upgrades) {
                if (currUPG.getType().equals(itemName)) {
                    actualUpgrade = currUPG;
                    if (currUPG.getCost() > actualPlayer.getMoney()) {
                        JOptionPane.showMessageDialog(null, "You're too broke for this.", "Phew, my dollars are saved!", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }
            
            int confirm = JOptionPane.showOptionDialog(
                GUI,
                "Are you sure you want to buy " + itemName + "?",
                "Purchase Confirmation", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null
            );

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, itemName + " successfully purchased!", "OK", JOptionPane.INFORMATION_MESSAGE);
                actualPlayer.setMoney(actualPlayer.getMoney() - actualUpgrade.getCost());
                dummy.writePlayer("umbrella_boy.csv", actualPlayer);
                actualUpgrade.setIsOwned(true);
                actualUpgrade.writeJsonFile();
            } else {
                JOptionPane.showMessageDialog(null, itemName + " purchase was cancelled.", "Phew, my dollars are saved!", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ItemNotFoundException inf) {
            JOptionPane.showMessageDialog(null, "Oops, item not found!", "OK", JOptionPane.INFORMATION_MESSAGE);
        } catch (PlayerNotFoundException ex) {
            Logger.getLogger(StoreMenuGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        StoreMenuGUI smgui = new StoreMenuGUI(previousFrame);
        smgui.setVisible(true);
        this.GUI.dispose();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        goBack(e);
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
