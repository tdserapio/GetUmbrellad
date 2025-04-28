package getumbrellad.views;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.controllers.LevelGameplayPanelGUIController;
import java.awt.*;
import javax.swing.*;

public class LevelGameplayGUI extends JFrame{
    
    private LevelGameplayPanelGUI panel;
    private LevelGameplayGUIController controller;
    private JLayeredPane layer;
    private JPanel pausePanel;
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
            pausePanel.setLocation(0,0);
            pausePanel.setSize(this.getSize());
            pausePanel.setVisible(true);
            pausePanel.setFocusable(true);
            pausePanel.setBackground(Color.GRAY);
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
