/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getumbrellad.views;

import java.awt.*;
import java.util.ArrayList;
import getumbrellad.controllers.InventoryGUIController;
import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;
import getumbrellad.models.exceptions.Upgrade;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InventoryGUI extends JFrame {
    
    private JPanel leftPanel, statsPanel, upgradesPanel, infoPanel;
    private JLabel coinText, healthText, nameText, effectsText, descriptionText;
    private JScrollPane upgradeScroll;
    private JButton exitButton;
    private JList upgradeList;
    private InventoryGUIController controller;
    
    private DefaultListModel<String> model;
    
    private JFrame previousFrame;
    
    public InventoryGUI(JFrame previousFrame) {
        
        super("Main Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLayout(new BorderLayout());
        
        Player dummy = new Player();
        Player currentPlayer;
        try {
            currentPlayer = dummy.readPlayer("umbrella_boy.csv");
        } catch (PlayerNotFoundException pnfe) {
            System.out.println("Player not found!");
            return;
        }
        
        Upgrade setup = new Upgrade();
        model = new DefaultListModel<>();
        upgradeList = new JList(model);
        for (Upgrade currentUpgrade: Upgrade.upgrades) {
            if (currentUpgrade.getIsOwned()) {
                model.addElement(currentUpgrade.getType());
            }
        }
        upgradeList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        this.add(leftPanel, BorderLayout.CENTER);
        
        statsPanel = new JPanel();
        leftPanel.add(statsPanel, BorderLayout.NORTH);
        
        upgradesPanel = new JPanel();
        upgradesPanel.setLayout(new BoxLayout(upgradesPanel, BoxLayout.Y_AXIS));
        
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        this.add(infoPanel, BorderLayout.EAST);
        
        coinText = new JLabel(currentPlayer.getMoney() + " coins");
        healthText = new JLabel(currentPlayer.getHP() + " HP");
        statsPanel.add(coinText);
        statsPanel.add(healthText);
        
        nameText = new JLabel("name");
        nameText.setAlignmentX(CENTER_ALIGNMENT);
        effectsText = new JLabel("effects");
        effectsText.setAlignmentX(CENTER_ALIGNMENT);
        descriptionText = new JLabel("description");
        descriptionText.setAlignmentX(CENTER_ALIGNMENT);
        
        infoPanel.add(nameText);
        infoPanel.add(effectsText);
        infoPanel.add(descriptionText);
        infoPanel.setBorder(new EmptyBorder(0, 80, 0, 80));
        infoPanel.setPreferredSize(new Dimension(300, 600));
        
        upgradeScroll = new JScrollPane(upgradesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        upgradeScroll.setViewportView(upgradeList);
        leftPanel.add(upgradeScroll, BorderLayout.CENTER); 
        
        exitButton = new JButton("Exit");
        this.add(exitButton, BorderLayout.SOUTH);
        
        this.previousFrame = previousFrame;
        
        controller = new InventoryGUIController(this, coinText, healthText, nameText, effectsText, descriptionText, exitButton, upgradeList, previousFrame);
        upgradeList.addListSelectionListener(controller); 
        exitButton.addActionListener(controller);
        
        this.setVisible(true);

    }
    
}
