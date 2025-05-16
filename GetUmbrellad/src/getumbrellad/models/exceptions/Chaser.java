/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayGUI;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Chaser enemy in the game.
 * The Chaser moves horizontally toward the player if the 
 * vertical distance between them is within a threshold.
 * It checks for collisions with the ground to prevent falling 
 * and deals damage on contact with the player in fixed time intervals.
 */
public class Chaser extends Character implements Spawnable {
    
       
    /**
     * The x-coordinate of the Chaser.
     */
    private int x;

    /**
     * The y-coordinate of the Chaser.
     */
    private int y;

    /**
     * The width of the Chaser.
     */
    private int width;

    /**
     * The height of the Chaser.
     */
    private int height;

    /**
     * Horizontal speed of the Chaser.
     */
    private double speed = 1.2;

    /**
     * Vertical threshold for whether or not to pursue the player.
     */
    private final int chaseThreshold = 100;

    /**
     * Hitbox for collision detection.
     */
    private Rectangle hitbox;

    /**
     * Image of the Chaser.
     */
    private final Image chaserImg = new ImageIcon(getClass().getResource("../../resources/chaser.png")).getImage();

    /**
     * Constructs a Chaser instance.
     *
     * @param lggui     reference to the game panel
     * @param x         initial x-coordinate
     * @param y         initial y-coordinate
     * @param width     width of the Chaser
     * @param height    height of the Chaser
     * @param gameTimer timer for repeatedly checking collisions
     */
    public Chaser(LevelGameplayGUI lggui, int x, int y, int width, int height, Timer gameTimer) {
        super(lggui, width, height, 40);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(x, y, width, height);
        this.damage = 30;
        
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (lggui != null && lggui.getController() != null && isCollidingWithPlayer()) {
                    lggui.getController().getPlayer().deductHP(damage);
                }
            }
        }, 0, 1000);
        
    }
    
    /**
     * Spawns the Chaser at the specified coordinates.
     *
     * @param x the spawn point x-coordinate
     * @param y the spawn point y-coordinate
     */
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the Chaser on screen. The image is flipped horizontally 
     * depending on the relative position of the player.
     *
     * @param gtd the graphics context to draw on
     */
    @Override
    public void draw(Graphics2D gtd) {
        int dx = lgGUI.getController().getPlayer().getX() - x;
        if (dx >= 0) {
            gtd.drawImage(chaserImg, x, y, lgGUI);
        } else {
            int imgWidth = chaserImg.getWidth(null);
            int imgHeight = chaserImg.getHeight(null);

            AffineTransform original = gtd.getTransform();

            gtd.translate(x + imgWidth, y);
            gtd.scale(-1, 1);

            gtd.drawImage(chaserImg, 0, 0, lgGUI);

            gtd.setTransform(original);
        }
    }
    
    /**
     * Checks if the probe intersects with any obstacles.
     *
     * @param groundProbe the rectangle used to check ground collision
     * @return true if intersecting an obstacle, false otherwise
     */
    public boolean isCollidingWithObstacle(Rectangle groundProbe) {
        
        for (Obstacle obs: lgGUI.getController().getObstacles()) {
            if (obs.getHitbox().intersects(groundProbe)) {
                return true;
            }
        }
        
        return false;
        
    }
    
    /**
     * Checks if the Chaser is currently colliding with the player.
     *
     * @return true if intersecting the player's hitbox, false otherwise
     */
    public boolean isCollidingWithPlayer() {
        Player currentPlayer = lgGUI.getController().getPlayer();
        return currentPlayer.getHitBox().intersects(hitbox);
        
    }

    /**
     * Updates the Chaser's state. If the player is vertically close enough,
     * the Chaser moves toward the player while ensuring it doesn’t fall off the platform.
     */
    @Override
    public void updateState() {
        
        int playerX = lgGUI.getController().getPlayer().getX();
        int playerY = lgGUI.getController().getPlayer().getY();

        if (Math.abs(playerY - y) <= chaseThreshold) {
            
            int direction = Integer.compare(playerX, x);
            int nextX = x + (int)(speed * direction);

            Rectangle futureHitbox = new Rectangle(nextX, y, width, height);
            Rectangle groundProbe = new Rectangle(
                futureHitbox.x + width / 2,
                futureHitbox.y + height,
                10,
                10
            );

            if (isCollidingWithObstacle(groundProbe)) {
                x = nextX;
            }
        }
        
        hitbox.x = x;
        hitbox.y = y;
        
    }
}
