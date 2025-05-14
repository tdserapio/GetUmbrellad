package getumbrellad.controllers;

import getumbrellad.models.exceptions.Enemy;
import getumbrellad.models.exceptions.NPC;
import getumbrellad.models.exceptions.Obstacle;
import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;
import getumbrellad.models.exceptions.Spawnable;
import getumbrellad.views.GameOverGUI;
import getumbrellad.views.LevelGameplayGUI;
import getumbrellad.views.PauseGUI;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LevelGameplayGUIController implements ActionListener, MouseListener, MouseMotionListener, KeyListener{

    private LevelGameplayGUI panel;
    
    private ArrayList<Spawnable> entities = new ArrayList<>();
    private boolean paused = false;
    private Timer gameTimer;
    private Player player;
    
    public LevelGameplayGUIController(LevelGameplayGUI panel) {
        
        this.panel = panel;
        this.gameTimer = new Timer();
        
        try {
            player = new Player("umbrella_boy.csv", this.panel);
        } catch (PlayerNotFoundException e) {
            System.err.println("LEVEL CANNOT BE LOADED, PLAYER NOT FOUND");
            return;
        }
        
        makeObstacles();
        
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    
                    ArrayList<Spawnable> entityCopy = new ArrayList<>(entities);
                    for (Spawnable entity: entityCopy) {
                        entity.updateState();
                    }
                    
                    if (player.getIsDead()) {
                        gameTimer.cancel();
                        GameOverGUI gogui = new GameOverGUI(true);
                        gogui.setVisible(true);
                        panel.getFrame().dispose();
                    }
                    
                    panel.repaint();           // schedule a redraw
                }
            }
        }, 0, 17);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {}

    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            player.setKeyLeft(true);
        }
        if (e.getKeyChar() == 'd') {
            player.setKeyRight(true);
        }
        if (e.getKeyChar() == ' ') {
            player.setKeyUp(true);
        }
    }

    @Override
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

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            togglePause();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.setXMouse(e.getX());
        player.setYMouse(e.getY());
    }

    @Override public void mouseDragged(MouseEvent e) {}

        
    public boolean isPaused() {
        return paused;
    }

    public void makeObstacles() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("getumbrellad/resources/misc_files/level1.csv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip header if necessary
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                
                String currentName = parts[0].trim();
                String currentType = parts[1].trim();
                int currX = Integer.parseInt(parts[2].trim());
                int currY = Integer.parseInt(parts[3].trim());
                int currWidth = Integer.parseInt(parts[4].trim());
                int currHeight = Integer.parseInt(parts[5].trim());
                
                if (currentType.equals("Obstacle")) {
                    entities.add(new Obstacle(currX, currY, currWidth, currHeight));
                } else if (currentType.equals("Spawn")) {
                    player.setPosition(currX, currY);
                    entities.add(player);
                } else if (currentType.equals("Shooter")) {
                    entities.add(new Enemy(this.panel, currentType, currX, currY, currWidth, currHeight, gameTimer));
                } else if (currentType.equals("NPC")) {
                    NPC currentNPC = new NPC(this.panel, currentName, currX, currY, currWidth, currHeight);
                    entities.add(currentNPC);
                    StoreMenuGUIController.NPCs.add(currentNPC);
                }
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Toggle pause and (optionally) show a PauseGUI overlay. */
    public void togglePause() {
        
        paused = !paused;

        if (paused) {
            // show your existing PauseGUI, then hide the play window
            PauseGUI pause = new PauseGUI(this.panel, player);
            pause.setVisible(true);
            panel.setVisible(false);
        } else {
            panel.setVisible(true);
            panel.requestFocusInWindow();
        }
        
    }
    
    public void findMouseDirection(MouseEvent e) {
        player.setXMouse(e.getX());
        player.setYMouse(e.getY());
    }
    
    public ArrayList<Obstacle> getObstacles() {
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        for (Spawnable s: entities) {
            if (s instanceof Obstacle) {
                obstacles.add((Obstacle)s);
            }
        }
        return obstacles;
    }
    
    public ArrayList<Spawnable> getSpawnables() {
        return entities;
    }
    
    public void addEntity(Spawnable s) {
        entities.add(s);
    }
    
    public void removeEntity(Spawnable s) {
        try {
            entities.remove(s);
        } catch (IndexOutOfBoundsException ioobe) {}
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPaused(boolean isPaused) {
        this.paused = isPaused;
    }
}

