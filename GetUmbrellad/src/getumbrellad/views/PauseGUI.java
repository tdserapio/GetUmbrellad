package getumbrellad.views;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.controllers.PauseGUIController;
import getumbrellad.models.exceptions.Player;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


public class PauseGUI extends JFrame{

    private PauseGUIController controller;
    private JButton menuButton, inventoryButton, storeButton, helpButton, resumeButton;
    private LevelGameplayGUI levelGameplayGUI;
    private Player currentPlayer;

    public PauseGUI(LevelGameplayGUI levelGameplayGUI, Player currentPlayer) {
        
        super("Pause Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setResizable(false);
        
        this.levelGameplayGUI = levelGameplayGUI;

        this.setLayout(new GridLayout(6, 1));
        this.setLocation(0,0);
        this.setVisible(true);
        this.setFocusable(true);
        this.setBackground(Color.GRAY);
        menuButton = new JButton("Menu");
        inventoryButton = new JButton("Inventory");
        storeButton = new JButton("Store");
        helpButton = new JButton("Help");
        resumeButton = new JButton("Resume");

        Dimension size = new Dimension(150, 80);

        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        for (int i = 0; i < 5; i++) {                
            JPanel basePanel = new JPanel();
            panels.add(basePanel);
            this.add(basePanel);
        }

        panels.get(0).add(new JTextArea("Game is paused"));
        this.add(panels.get(0));
        
        resumeButton.setAlignmentX(CENTER_ALIGNMENT);
        resumeButton.setPreferredSize(size);
        resumeButton.setBackground(null);
        panels.get(1).add(resumeButton);
        this.add(panels.get(1));

        inventoryButton.setAlignmentX(CENTER_ALIGNMENT);
        inventoryButton.setPreferredSize(size);
        inventoryButton.setBackground(null);
        panels.get(2).add(inventoryButton);
        this.add(panels.get(2));

        helpButton.setAlignmentX(CENTER_ALIGNMENT);
        helpButton.setPreferredSize(size);
        helpButton.setBackground(null);
        panels.get(3).add(helpButton);
        this.add(panels.get(3));
        
        menuButton.setAlignmentX(CENTER_ALIGNMENT);
        menuButton.setPreferredSize(size);
        menuButton.setBackground(null);
        panels.get(4).add(menuButton);
        this.add(panels.get(4));

        controller = new PauseGUIController(this, menuButton, inventoryButton, helpButton, resumeButton, levelGameplayGUI, currentPlayer);
        menuButton.addActionListener(controller);
        inventoryButton.addActionListener(controller);            
        helpButton.addActionListener(controller);
        resumeButton.addActionListener(controller);
        menuButton.addMouseListener(controller);
        inventoryButton.addMouseListener(controller);
        helpButton.addMouseListener(controller);
        resumeButton.addMouseListener(controller);
        this.addKeyListener(controller);
            
    
        SwingUtilities.updateComponentTreeUI(this);
    }
    
            
            
}
