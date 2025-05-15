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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreMenuGUIController implements ActionListener, MouseListener {
    
    private LevelGameplayGUI lggui;
    private JFrame GUI;
    private JButton exitButton;
    private JPanel itemsPanel;
    private Player currentPlayer;
    private NPC currentNPC;
    private ArrayList<JDialog> dialogs = new ArrayList<JDialog>();
    private boolean mouseInsideTooltip = false, mouseInsideItemPanel;
    private JWindow currentWindow = null;
    private Timer hoverTimer = null;
    private Component hoveredComponent = null;
    private int delay = 200;
    
    public static ArrayList<NPC> NPCs = new ArrayList<>();
    
    public StoreMenuGUIController(JFrame GUI, JButton exitButton, JPanel itemsPanel, LevelGameplayGUI lggui, Player currentPlayer, NPC currentNPC) {
        this.GUI = GUI;
        this.exitButton = exitButton;
        this.itemsPanel = itemsPanel;
        this.lggui = lggui;
        this.currentPlayer = currentPlayer;
        this.currentNPC = currentNPC;

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
                if (currUPG.getName().equals(itemName)) {
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
                JOptionPane.showMessageDialog(GUI, itemName + " successfully purchased!", "OK", JOptionPane.INFORMATION_MESSAGE);
                this.currentPlayer.setMoney(this.currentPlayer.getMoney() - actualUpgrade.getCost());
                this.currentPlayer.getPlayerUpgrades().add(actualUpgrade);
                this.currentPlayer.applyUpgrade(actualUpgrade);
                actualUpgrade.setIsOwned(true);
            } else {
                JOptionPane.showMessageDialog(GUI, itemName + " purchase was cancelled.", "Phew, my dollars are saved!", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ItemNotFoundException inf) {
            JOptionPane.showMessageDialog(GUI, "Oops, item not found!", "OK", JOptionPane.INFORMATION_MESSAGE);
        }
        
        //remove tooltip
        if (currentWindow != null) {
            currentWindow.dispose();
            currentWindow = null;
        }
        mouseInsideTooltip = false;
        mouseInsideItemPanel = false;

        StoreMenuGUI smgui = new StoreMenuGUI(lggui, this.currentPlayer, this.currentNPC);
        smgui.setVisible(true);
        this.GUI.dispose();
        
    }

    public void showDescription(MouseEvent e) {
        
        hoveredComponent = (Component) e.getSource();
        
        if (hoverTimer != null && hoverTimer.isRunning()) {
            hoverTimer.stop();
        }
        
        hoverTimer = new Timer(delay, event -> {
            String itemName = hoveredComponent.getName();
            
            int components = 0;
            int width = 0;
            for (Component itemPanel : itemsPanel.getComponents()) {
                components++;
            }
            if (components == 1) {
                width = 350;
            } else if (components == 2) {
                width = 280;
            } else if (components == 3) {
                width = 210;
            } else if (components == 0) {
                System.out.println("Shop is empty");
            } else {
                System.out.println("Error more or less than 3 components");
            }
            
            for (Upgrade currUPG: Upgrade.upgrades) {
                String upgradeName = currUPG.getName().toLowerCase().replaceAll("\\s+", "");
                if (upgradeName.equals(itemName)) {  
                    String points = "ERROR";
                    if (currUPG.getValue() == 1) {
                        points = " point!";
                    }
                    else {
                        points = " points!!";
                    }
                    String description = 
                    "<html><body style='width:" + width + "px; padding:10px;'><h1>"
                    + currUPG.getDescription()
                    + "</h1><h2> Increases your "
                    + currUPG.getIncreasedStat()
                    + " by " + currUPG.getValue() + points
                    + "</h2></body></html>";
                
                    if (currentWindow != null) {
                        currentWindow.dispose();
                        currentWindow = null;
                    }
                
                    JWindow window = new JWindow();
                    JLabel label = new JLabel(description);
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    label.setBackground(Color.decode("#E5E5E5"));
                    label.setOpaque(true);
                    window.getContentPane().add(label);
                    window.pack();
                    
                    window.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            mouseInsideTooltip = true;
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            mouseInsideTooltip = false;
                        
                            new Timer(delay, event -> {
                                if (!mouseInsideTooltip && !mouseInsideItemPanel) {
                                    if (currentWindow != null) {
                                        currentWindow.dispose();
                                        currentWindow = null;
                                    }
                                }
                            }).start();
                        }
                    });
                
                    Point location = hoveredComponent.getLocationOnScreen();
                    int x = location.x + (hoveredComponent.getWidth() / 2) - (window.getWidth() / 2);
                    int y = location.y - window.getHeight() + 400;
                    window.setLocation(x, y);
                    currentWindow = window;
                    window.setVisible(true);
                    break;
                }           
            }
        });
    
        hoverTimer.setRepeats(false);
        hoverTimer.start();
        
    }
    
    public void hideDescription(MouseEvent e) {
        mouseInsideItemPanel = false;
        
        new Timer(delay, event -> {
            if (!mouseInsideTooltip && !mouseInsideItemPanel) {
                if (currentWindow != null) {
                    currentWindow.dispose();
                    currentWindow = null;
                }
            }
        }).start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        goBack(e);
    }

    @Override    
    public void mouseEntered(MouseEvent e){
        mouseInsideItemPanel = true;
        showDescription(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hideDescription(e);
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
