package getumbrellad.controllers;

import getumbrellad.models.exceptions.Boss;
import getumbrellad.models.exceptions.Chaser;
import getumbrellad.models.exceptions.Coin;
import getumbrellad.models.exceptions.LevelDoesNotExistException;
import getumbrellad.models.exceptions.Shooter;
import getumbrellad.models.exceptions.NPC;
import getumbrellad.models.exceptions.Obstacle;
import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.PlayerNotFoundException;
import getumbrellad.models.exceptions.Portal;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class LevelGameplayGUIController implements ActionListener, MouseListener, MouseMotionListener, KeyListener{

    private LevelGameplayGUI panel;
    
    private ArrayList<Spawnable> entities = new ArrayList<>();
    private boolean paused = false;
    private Timer gameTimer;
    private Player player;
    
    private boolean hasLoadedPlayer = false;
    
    private JButton menuButton;
    private JLabel coinLabel, hpLabel;
    
    public LevelGameplayGUIController(LevelGameplayGUI panel) {
        
        this.panel = panel;
        this.gameTimer = new Timer();
        
        try {
            
            player = new Player("umbrella_boy.csv", this.panel);
            makeObstacles(player);
            
            this.hasLoadedPlayer = true;
            
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

                        if (coinLabel != null) coinLabel.setText(player.getMoney() + "");
                        if (hpLabel != null) hpLabel.setText(player.getHP() + "");

                        panel.repaint();           // schedule a redraw
                    }
                }
            }, 0, 17);

        } catch (LevelDoesNotExistException ldne) {
            System.err.println("The level cannot be loaded.");
        } catch (PlayerNotFoundException pnfe) {
            System.err.println("The player cannot be loaded.");
        }
        
    }
    
    public void setupFrontend(JButton menuButton, JLabel coinLabel, JLabel hpLabel) {
        this.menuButton = menuButton;
        this.coinLabel = coinLabel;
        this.hpLabel = hpLabel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    
        if (menuButton != null && e.getSource() == menuButton) {
            if (!paused) togglePause();
        }
        
    }

    public boolean getHasLoadedPlayer() {
        return hasLoadedPlayer;
    }
    
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

    // makes level in general
    public void makeObstacles(Player player) throws LevelDoesNotExistException {
        
        if (!(1 <= player.getLevel() && player.getLevel() <= 6)) {
            throw new LevelDoesNotExistException();
        }
        
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("getumbrellad/resources/misc_files/level" + player.getLevel() + ".csv");

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
                    entities.add(new Shooter(this.panel, currX, currY, currWidth, currHeight, gameTimer));
                } else if (currentType.equals("NPC")) {
                    NPC currentNPC = new NPC(this.panel, currentName, currX, currY, currWidth, currHeight, this.player);
                    entities.add(currentNPC);
                    StoreMenuGUIController.NPCs.add(currentNPC);
                } else if (currentType.equals("Coin")) {
                    entities.add(new Coin(this.panel, currX, currY));
                } else if (currentType.equals("Chaser")) {
                    entities.add(new Chaser(this.panel, currX, currY, currWidth, currHeight, gameTimer));
                } else if (currentType.equals("Portal")) {
                    entities.add(new Portal(this.panel, currX, currY));
                } else if (currentType.equals("Boss")) {
                    entities.add(new Boss(this.panel, currX, currY, gameTimer));
                }
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    
    public void refreshLevel() {
        
        if (!paused) paused = true;
        gameTimer.cancel();
        
        SwingUtilities.invokeLater(() -> {
            
            JFrame oldFrame = panel.getFrame();
            oldFrame.dispose();
            
            if (player.getLevel() == 7) {
                gameTimer.cancel();
                GameOverGUI gogui = new GameOverGUI(false);
                gogui.setVisible(true);
                gogui.requestFocusInWindow();
            } else {
                LevelGameplayGUI newPanel = new LevelGameplayGUI();
                newPanel.getFrame().setVisible(true);
            }
            
        });
        
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

