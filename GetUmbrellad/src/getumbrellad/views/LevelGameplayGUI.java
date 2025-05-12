package getumbrellad.views;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.models.exceptions.Obstacle;
import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private boolean paused = false;

    private Player player;
    private ArrayList<Obstacle> obstacles;
    private Timer gameTimer;
    private LevelGameplayGUIController controller;

    public LevelGameplayGUI() {
        
        obstacles = new ArrayList<>();
        gameTimer = new Timer();

        frame = new JFrame("Level Gameplay");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);

        setFocusable(true);
        
        System.out.println(this instanceof LevelGameplayGUI);

        try {
            player = new Player("umbrella_boy.csv", this);
        } catch (PlayerNotFoundException e) {
            System.err.println("LEVEL CANNOT BE LOADED, PLAYER NOT FOUND");
            return;
        }
        makeObstacles();
        
        controller = new LevelGameplayGUIController(this, player);
        this.addKeyListener(controller);
        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);

        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    player.updateState();        // update physics / state
                    repaint();           // schedule a redraw
                }
            }
        }, 0, 17);
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Obstacle ob : obstacles) {
            ob.draw(g2);
        }
        if (player != null) {
            player.draw(g2);
        }
    }

    public void makeObstacles() {
        obstacles.add(new Obstacle(0, 520, 900, 50));
        obstacles.add(new Obstacle(0, 0, 40, 600));
        obstacles.add(new Obstacle(850, 0, 900, 600));
        obstacles.add(new Obstacle(250, 350, 150, 50));
        obstacles.add(new Obstacle(450, 200, 300, 50));
    }

    /** Toggle pause and (optionally) show a PauseGUI overlay. */
    public void togglePause() {
        
        paused = !paused;

        if (paused) {
            // show your existing PauseGUI, then hide the play window
            PauseGUI pause = new PauseGUI(this, player);
            pause.setVisible(true);
            frame.setVisible(false);
        } else {
            frame.setVisible(true);
            requestFocusInWindow();
        }
        
    }
    
    public void findMouseDirection(MouseEvent e) {
        player.setXMouse(e.getX());
        player.setYMouse(e.getY());
    }
    
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

}
