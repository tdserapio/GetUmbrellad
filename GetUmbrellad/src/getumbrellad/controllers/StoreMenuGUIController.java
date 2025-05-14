package getumbrellad.controllers;

import getumbrellad.views.StoreMenuGUI;
import getumbrellad.views.LevelGameplayGUI;
import getumbrellad.models.exceptions.ItemNotFoundException;
import getumbrellad.models.exceptions.NPC;
import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;
import getumbrellad.models.exceptions.Upgrade;
import static getumbrellad.models.exceptions.Upgrade.upgrades;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreMenuGUIController implements ActionListener, MouseListener {
    
    private LevelGameplayGUI lggui;
    private JFrame GUI;
    private JButton exitButton;
    private JPanel itemsPanel;
    private Player currentPlayer;
    private ArrayList<Upgrade> canBeBought;
    
    public static ArrayList<NPC> NPCs = new ArrayList<>();
    
    public StoreMenuGUIController(JFrame GUI, JButton exitButton, JPanel itemsPanel, LevelGameplayGUI lggui, Player currentPlayer, ArrayList<Upgrade> canBeBought) {
        this.GUI = GUI;
        this.exitButton = exitButton;
        this.itemsPanel = itemsPanel;
        this.lggui = lggui;
        this.currentPlayer = currentPlayer;
        this.canBeBought = canBeBought;
        
    }
    
    public void goBack(ActionEvent e){
        
        if (e.getSource() == exitButton) {
            lggui.getController().setPaused(false);
            lggui.setVisible(true);
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
            
            Upgrade actualUpgrade = null;
            
            for (Upgrade currUPG: Upgrade.upgrades) {
                if (currUPG.getType().equals(itemName)) {
                    actualUpgrade = currUPG;
                    if (currUPG.getCost() > this.currentPlayer.getMoney()) {
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
                this.currentPlayer.setMoney(this.currentPlayer.getMoney() - actualUpgrade.getCost());
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
        
        StoreMenuGUI smgui = new StoreMenuGUI(lggui, this.currentPlayer, canBeBought);
        smgui.setVisible(true);
        this.GUI.dispose();
        
    }

    
    public void loadAllStoresJsonFile() {
        
        if (Upgrade.upgrades != null) {
            Upgrade.upgrades.clear();
        }
        
        for (NPC currNPC: NPCs) {
            currNPC.getNPCUpgrades().clear();
            currNPC.getStoreMenuGUI().setCanBeBought(currNPC.getNPCUpgrades());
        }
        // Path is relative to src/main/resources or classpath root
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("getumbrellad/resources/misc_files/stores.csv");

        if (inputStream == null) {
            System.out.println("File not found!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip header if necessary
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                String npcName = parts[0].trim();
                String upgradeName = parts[1].trim();
                int upgradeValue = Integer.parseInt(parts[2].trim());
                String increasedStat = parts[3].trim();
                String upgradeDescription = parts[4].trim();
                boolean upgradeOwned = (Integer.parseInt(parts[5].trim()) > 0);
                int upgradeCost = Integer.parseInt(parts[6].trim());
                                
                Upgrade currUPG = new Upgrade(upgradeName, upgradeValue, increasedStat, upgradeDescription, upgradeOwned, upgradeCost);
                Upgrade.upgrades.add(currUPG);
                
                for (NPC currNPC: NPCs) {
                    if (currNPC.getName().equals(npcName)) {
                        if (!currNPC.getNPCUpgrades().contains(currUPG) && !currentPlayer.getPlayerUpgrades().contains(currUPG)) { //if current NPC and player does not have the upgrade
                            currNPC.addUpgrade(currUPG);
                            currNPC.getStoreMenuGUI().setCanBeBought(currNPC.getNPCUpgrades());
                        }
                    }
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
