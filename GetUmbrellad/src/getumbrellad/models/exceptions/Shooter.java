package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayGUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import java.util.Random;

/**
 * Represents a shooter in the game.
 * The Shooter tracks the player's position, calculates an angle, and 
 * shoots bullets toward the Player.
 */
public class Shooter extends Character implements Spawnable {
    
    /**
     * The x-coordinate of the Shooter.
     */
    private int x;

    /**
     * The y-coordinate of the Shooter.
     */
    private int y;

    /**
     * The width of the Shooter.
     */
    private int width;

    /**
     * The height of the Shooter.
     */
    private int height;

    /**
     * Timer used to schedule bullet firing intervals.
     */
    private Timer enemyTimer;

    /**
     * The angle (in radians) from the Shooter to the player.
     */
    private double thetaToPlayer;

    /**
     * The image used to visually represent the Shooter.
     */
    private final Image enemyImg = new ImageIcon(getClass().getResource("../../resources/shooter.png")).getImage();

    /**
     * Creates a Shooter enemy.
     *
     * @param lggui     the game GUI where the Shooter will operate
     * @param x         the x-coordinate of the Shooter
     * @param y         the y-coordinate of the Shooter
     * @param width     the width of the Shooter
     * @param height    the height of the Shooter
     * @param gameTimer the shared timer used for scheduling bullet firing
     */
    public Shooter(LevelGameplayGUI lggui, int x, int y, int width, int height, Timer gameTimer) {
        
        super(lggui, width, height, 40);
        
        this.x = x;
        this.y = y;
        this.enemyTimer = gameTimer;
        
        Random rand = new Random();
        
        enemyTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (lggui != null && lggui.getController() != null && !lggui.getController().isPaused()) {
                    lggui.getController().addEntity(new Bullet(lggui, thetaToPlayer, x, y, 10, 20));
                }
            }
        }, rand.nextInt(1000) + 1, 3000);
        
    }

    /**
     * Spawns the Shooter at the specified coordinates.
     *
     * @param x the x-coordinate to spawn at
     * @param y the y-coordinate to spawn at
     */
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Draws the Shooter on the screen.
     * The image is flipped horizontally if the player is to 
     * the left of the Shooter.
     *
     * @param gtd the graphics context used
     */
    @Override
    public void draw(Graphics2D gtd) {
        if (Math.toDegrees(thetaToPlayer) <= 90 || Math.toDegrees(thetaToPlayer) >= 270) {
            gtd.drawImage(enemyImg, x, y, lgGUI);
        } else {
            int imgWidth = enemyImg.getWidth(null);
            int imgHeight = enemyImg.getHeight(null);

            AffineTransform original = gtd.getTransform();

            gtd.translate(x + imgWidth, y);
            gtd.scale(-1, 1);

            gtd.drawImage(enemyImg, 0, 0, lgGUI);

            gtd.setTransform(original);
            
        }
    }
    
    /**
     * Updates the Shooter's angle toward the player each frame.
     * This angle is used for firing bullets in the correct direction.
     */
    @Override
    public void updateState() {
        double dx = lgGUI.getController().getPlayer().getX() - x;
        double dy = lgGUI.getController().getPlayer().getY() - y;
        thetaToPlayer = Math.atan2(dy, dx);
    }
    
}
