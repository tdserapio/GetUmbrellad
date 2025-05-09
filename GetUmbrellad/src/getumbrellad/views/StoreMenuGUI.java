package getumbrellad.views;

import getumbrellad.controllers.StoreMenuGUIController;
import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;
import getumbrellad.models.exceptions.Upgrade;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class StoreMenuGUI extends JFrame {
    
    private JPanel statsPanel, itemsPanel, buttonPanel, coinPanel;
    private JLabel hpLabel, coinLabel;
    private JButton exitButton, nextButton;
    
    private StoreMenuGUIController controller;
    
    private JFrame previousFrame;
    
    public StoreMenuGUI(JFrame previousFrame) {
        
        super("Store Menu");
        this.setLayout(new BorderLayout());
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Player dummy = new Player();
        Player currentPlayer;
        try {
            currentPlayer = dummy.readPlayer("umbrella_boy.csv");
        } catch (PlayerNotFoundException pnfe) {
            System.out.println("Player not found!");
            return;
        }
        
        // Stats Panel
        statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), 
                "Player Stats"
            )
        );

        // Coin Panel
        coinPanel = new JPanel();
        coinPanel.setLayout(new BoxLayout(coinPanel, BoxLayout.X_AXIS));

        hpLabel = new JLabel("HP: " + currentPlayer.getHP());
        coinLabel = new JLabel("\t\t\t\t\t\t\t\t\tCoins: " + currentPlayer.getMoney());

        coinPanel.add(hpLabel);
        coinPanel.add(coinLabel);
        
        statsPanel.add(coinPanel, BorderLayout.EAST);
        
        Upgrade setup = new Upgrade();
        ArrayList<Upgrade> canBeBought = new ArrayList<>();
        for (Upgrade currUPG: Upgrade.upgrades) {
            if (!currUPG.getIsOwned()) {
                canBeBought.add(currUPG);
            }
        }
        
        // Items Panel
        itemsPanel = new JPanel(new GridLayout(1, canBeBought.size(), 10, 10));
        itemsPanel.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), 
                "Store Items"
            )
        );

        for (int i = 0; i < canBeBought.size(); i++) {
            
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            
            try {
                JLabel picLabel = new JLabel(new ImageIcon(StoreMenuGUI.class.getResource("../resources/placeholder.png")), SwingConstants.CENTER);
                itemPanel.add(picLabel, BorderLayout.NORTH);
            } catch (Exception fileError) {
                JLabel itemFrame = new JLabel("[ ]", SwingConstants.CENTER);
                itemPanel.add(itemFrame, BorderLayout.NORTH);
            }
            
            JLabel itemLabel = new JLabel(canBeBought.get(i).getType(), SwingConstants.CENTER);
            itemPanel.add(itemLabel, BorderLayout.SOUTH);
            
            itemsPanel.add(itemPanel);
            
        }

        
        // Button Panel
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        nextButton = new JButton("NEXT");
        buttonPanel.add(nextButton, BorderLayout.CENTER);

        exitButton = new JButton("EXIT");
        buttonPanel.add(exitButton, BorderLayout.CENTER);

        // Adding panels to JFrame
        this.add(statsPanel, BorderLayout.NORTH);
        this.add(itemsPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        controller = new StoreMenuGUIController(this, exitButton, itemsPanel, previousFrame);
        exitButton.addActionListener(controller);
        exitButton.addMouseListener(controller);
        
        for (Component itemPanel : itemsPanel.getComponents()) {
            itemPanel.addMouseListener(controller);
        }
        
        this.setVisible(true);
        
    }

}
