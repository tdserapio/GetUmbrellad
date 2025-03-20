package getumbrellad.views;

import getumbrellad.controllers.StoreMenuGUIController;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StoreMenuGUI extends JFrame {
    
    private JPanel statsPanel, itemsPanel, buttonPanel, coinPanel;
    private JLabel coinFrame, coinLabel;
    private JButton exitButton;
    
    private StoreMenuGUIController controller;
    
    public StoreMenuGUI() {
        
        super("Store Menu");
        this.setLayout(new BorderLayout());
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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

        coinFrame = new JLabel("🪙");
        coinLabel = new JLabel("100");

        coinPanel.add(coinFrame);
        coinPanel.add(coinLabel);
        
        statsPanel.add(coinPanel, BorderLayout.EAST);

        
        // Items Panel
        itemsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        itemsPanel.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), 
                "Store Items"
            )
        );

        for (int i = 0; i < 3; i++) {
            
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            
            try {
                JLabel picLabel = new JLabel(new ImageIcon(StoreMenuGUI.class.getResource("../resources/placeholder.png")), SwingConstants.CENTER);
                itemPanel.add(picLabel, BorderLayout.NORTH);
            } catch (Exception fileError) {
                JLabel itemFrame = new JLabel("[ ]", SwingConstants.CENTER);
                itemPanel.add(itemFrame, BorderLayout.NORTH);
            }
            
            JLabel itemLabel = new JLabel("ITEM " + (i + 1), SwingConstants.CENTER);
            itemPanel.add(itemLabel, BorderLayout.SOUTH);
            
            itemsPanel.add(itemPanel);
            
        }

        
        // Button Panel
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        exitButton = new JButton("EXIT");
        buttonPanel.add(exitButton, BorderLayout.CENTER);

        // Adding panels to JFrame
        this.add(statsPanel, BorderLayout.NORTH);
        this.add(itemsPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        controller = new StoreMenuGUIController(this, exitButton, itemsPanel);
        exitButton.addActionListener(controller);
        exitButton.addMouseListener(controller);
        
        for (Component itemPanel : itemsPanel.getComponents()) {
            itemPanel.addMouseListener(controller);
        }
        
        this.setVisible(true);
        
    }

}
