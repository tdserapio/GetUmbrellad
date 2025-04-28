package getumbrellad.views;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.controllers.LevelGameplayPanelGUIController;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class LevelGameplayGUI extends JFrame{
    
    private LevelGameplayPanelGUI panel;
    private LevelGameplayGUIController controller;
    private JLayeredPane layer;
    private JPanel pausePanel;
    private JButton menuButton, inventoryButton, storeButton, helpButton, resumeButton;
    private boolean isPaused = false;
    
    public LevelGameplayGUI() {
        super("Level Gameplay");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);

        panel = new LevelGameplayPanelGUI(this);
        panel.setLocation(0,0);
        panel.setSize(this.getSize());
        panel.setVisible(true);
        panel.setFocusable(true);
        this.add(panel);
        
        //don't change this
        addMouseMotionListener(new LevelGameplayPanelGUIController(panel));
        addKeyListener(new LevelGameplayPanelGUIController(panel));
        this.setFocusable(true);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void pauseGame() {
        System.out.println("boom");
        isPaused = !isPaused;

        if (isPaused) {
            panel.pause();
            this.remove(panel);

            pausePanel = new JPanel();
            pausePanel.setLayout(new GridLayout(6, 1));
            pausePanel.setLocation(0,0);
            pausePanel.setVisible(true);
            pausePanel.setFocusable(true);
            pausePanel.setBackground(Color.GRAY);

            menuButton = new JButton("Menu");
            inventoryButton = new JButton("Inventory");
            storeButton = new JButton("Store");
            helpButton = new JButton("Help");
            resumeButton = new JButton("Resume");

            Dimension size = new Dimension(150, 80);

            ArrayList<JPanel> panels = new ArrayList<JPanel>();
            for (int i = 0; i < 6; i++) {
                JPanel basePanel = new JPanel();
                panels.add(basePanel);
                pausePanel.add(basePanel);
            }

            panels.get(0).add(new JTextArea("Game is paused"));
            pausePanel.add(panels.get(0));
            
            menuButton.setAlignmentX(CENTER_ALIGNMENT);
            menuButton.setPreferredSize(size);
            panels.get(1).add(menuButton);
            pausePanel.add(panels.get(1));

            inventoryButton.setAlignmentX(CENTER_ALIGNMENT);
            inventoryButton.setPreferredSize(size);
            panels.get(2).add(inventoryButton);
            pausePanel.add(panels.get(2));

            storeButton.setAlignmentX(CENTER_ALIGNMENT);
            storeButton.setPreferredSize(size);
            panels.get(3).add(storeButton);
            pausePanel.add(panels.get(3));

            helpButton.setAlignmentX(CENTER_ALIGNMENT);
            helpButton.setPreferredSize(size);
            panels.get(4).add(helpButton);
            pausePanel.add(panels.get(4));

            resumeButton.setAlignmentX(CENTER_ALIGNMENT);
            resumeButton.setPreferredSize(size);
            panels.get(5).add(resumeButton);
            pausePanel.add(panels.get(5));

            controller = new LevelGameplayGUIController(this, menuButton, inventoryButton, storeButton, helpButton, resumeButton);
            menuButton.addActionListener(controller);
            inventoryButton.addActionListener(controller);
            storeButton.addActionListener(controller);
            helpButton.addActionListener(controller);
            resumeButton.addActionListener(controller);
            menuButton.addMouseListener(controller);
            inventoryButton.addMouseListener(controller);
            storeButton.addMouseListener(controller);
            helpButton.addMouseListener(controller);
            resumeButton.addMouseListener(controller);
            
            this.add(pausePanel);
        
            SwingUtilities.updateComponentTreeUI(this);
        }
        else {
            panel.unpause();
            this.remove(pausePanel);
            this.add(panel);

            SwingUtilities.updateComponentTreeUI(this);
        }
        
        
        
    }
}
