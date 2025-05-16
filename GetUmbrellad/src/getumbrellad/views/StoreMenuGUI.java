package getumbrellad.views;

import getumbrellad.controllers.StoreMenuGUIController;
import getumbrellad.models.exceptions.NPC;
import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;
import getumbrellad.models.exceptions.Upgrade;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class StoreMenuGUI extends JFrame {
    
    private LevelGameplayGUI lggui;
    private JPanel statsPanel, itemsPanel, buttonPanel, coinPanel;
    private JLabel hpLabel, coinLabel;
    private JButton exitButton, nextButton;
    
    private StoreMenuGUIController controller;
    
    private Player currentPlayer;
    private NPC currentNPC;
    private JFrame previousFrame;
    
    private ArrayList<Upgrade> canBeBought = new ArrayList<>();
    
    public StoreMenuGUI(LevelGameplayGUI lggui, Player currentPlayer, NPC currentNPC) {
        
        super("Store Menu");
        this.setLayout(new BorderLayout());
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        this.currentPlayer = currentPlayer;
        this.currentNPC = currentNPC;
        
        this.canBeBought = currentNPC.getNPCUpgrades();
        
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream is = getClass().getResourceAsStream("../resources/Lexend.ttf");
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, is));
        } catch (Exception anyException) {
            System.out.println(anyException.getMessage());
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
        hpLabel.setFont(new Font("Lexend", Font.TRUETYPE_FONT, 16));
        coinLabel = new JLabel("\t\t\t\t\t\t\t\t\tCoins: " + currentPlayer.getMoney());
        coinLabel.setFont(new Font("Lexend", Font.TRUETYPE_FONT, 16));

        coinPanel.add(hpLabel);
        coinPanel.add(coinLabel);
        
        statsPanel.add(coinPanel, BorderLayout.EAST);
        
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
            
            JLabel itemLabel = new JLabel(canBeBought.get(i).getName(), SwingConstants.CENTER);
            itemLabel.setFont(new Font("Lexend", Font.BOLD, 32));
            itemPanel.add(itemLabel, BorderLayout.NORTH);
            
            String currentUpgradeName = canBeBought.get(i).getName();
            currentUpgradeName = currentUpgradeName.replaceAll("\\s+", "").toLowerCase();
            
            try {
                JLabel picLabel = new JLabel(new ImageIcon(StoreMenuGUI.class.getResource("../resources/" + currentUpgradeName + ".png")), SwingConstants.CENTER);
                itemPanel.add(picLabel, BorderLayout.SOUTH);
                itemPanel.setName(currentUpgradeName);
            } catch (Exception fileError) {
                JLabel itemFrame = new JLabel("[ ]", SwingConstants.CENTER);
                itemPanel.add(itemFrame, BorderLayout.SOUTH);
            }
            
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
        
        controller = new StoreMenuGUIController(this, exitButton, itemsPanel, lggui, currentPlayer, currentNPC);
        exitButton.addActionListener(controller);
        exitButton.addMouseListener(controller);
        
        for (Component itemPanel : itemsPanel.getComponents()) {
            itemPanel.addMouseListener(controller);
        }
        
        this.setVisible(true);
        
    }
    
    public StoreMenuGUIController getController() {
        return controller;
    }
    
    public ArrayList<Upgrade> getCanBeBought() {
        return this.canBeBought;
    }
    
    public void setCanBeBought(ArrayList<Upgrade> npcUpgrades) {
        this.canBeBought = npcUpgrades;
    }

}
