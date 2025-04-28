package getumbrellad.views;

import getumbrellad.models.exceptions.Obstacle;
import getumbrellad.models.exceptions.Player;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.JPanel;

public class LevelGameplayPanelGUI extends JPanel {
    
    private Player player;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private Timer gameTimer;
    private LevelGameplayGUI frame;
    
    public LevelGameplayPanelGUI(LevelGameplayGUI frame) {
        
        this.frame = frame;
        
        player = new Player("Umbrella Boy", this, 400, 300, 20, 20, 5);
        //in the future use an arraylist for enemies, npcs, and more
        makeObstacles();
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask(){
            
            @Override
            public void run() {
                player.set();
                repaint();
            }
            
        }, 0, 17);
    }
    
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
    
    public void paint(Graphics g) {
    
        super.paint(g);
        Graphics2D gtd = (Graphics2D) g;
        player.draw(gtd);
        
        for (Obstacle obstacle: obstacles) {
            obstacle.draw(gtd);
        }
    
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            player.setKeyLeft(true);
        }
        if (e.getKeyChar() == 'd') {
            player.setKeyRight(true);
        }
        
        //remove space bar jumping later
        if (e.getKeyChar() == ' ') {
            player.setKeyUp(true);
        }
        
    }
    
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            player.setKeyLeft(false);
        }
        if (e.getKeyChar() == 'd') {
            player.setKeyRight(false);
        }
        
        //remove space bar jumping later
        if (e.getKeyChar() == ' ') {
            player.setKeyUp(false);
        }
        
    }

    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            System.out.println("boom");
            frame.pauseGame();
        }
    }
    
    public void findMouseDirection(MouseEvent e) {
        player.setXMouse(e.getX());
        player.setYMouse(e.getY());
    }
    
    public void setPlayerJumpToggle() {
        player.setJumpToggle(true);
    }
    
    public void makeObstacles() {
        obstacles.add(new Obstacle(0, 520, 900, 50));
        obstacles.add(new Obstacle(0, 0, 40, 600));
        obstacles.add(new Obstacle(850, 0, 900, 600));
        obstacles.add(new Obstacle(250, 350, 150, 50));
        obstacles.add(new Obstacle(450, 200, 300, 50));
    }
}
