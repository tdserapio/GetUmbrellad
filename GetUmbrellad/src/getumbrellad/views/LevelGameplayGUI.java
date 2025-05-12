package getumbrellad.views;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.models.exceptions.Enemy;
import getumbrellad.models.exceptions.Obstacle;
import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;
import getumbrellad.models.exceptions.Spawnable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A single class that combines the old JFrame-level and JPanel-level
 * responsibilities.  The class itself is a JPanel (for paint/double-buffering)
 * but it also constructs and owns its enclosing window.
 */
public class LevelGameplayGUI extends JPanel {

    private final JFrame frame;
    private LevelGameplayGUIController controller;

    public LevelGameplayGUI() {

        frame = new JFrame("Level Gameplay");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);

        setFocusable(true);
        
        this.controller = new LevelGameplayGUIController(this);
        this.addKeyListener(controller);
        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);
        
    }
    
    public Frame getFrame() {
        return frame;
    }
    
    public LevelGameplayGUIController getController() {
        return controller;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        ArrayList<Spawnable> entities = new ArrayList<>(controller.getSpawnables());

        for (Spawnable spawn : entities) {
            spawn.draw(g2);
        }
        
    }

}
