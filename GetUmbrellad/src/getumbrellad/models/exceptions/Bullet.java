package getumbrellad.models.exceptions;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.views.LevelGameplayGUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Represents a Bullet in the game. A Bullet moves in a fixed direction (defined by theta), 
 * checks for collisions with obstacles or the player's umbrella,
 * and destroys itself when it hits something.
 */
public class Bullet implements Spawnable {
    
        
    /**
     * The game GUI where it resides.
     */
    private LevelGameplayGUI lggui;

    /**
     * The controller that manages game logic, accessible from GUI.
     */
    private LevelGameplayGUIController lgController;

    /**
     * The direction of movement of the bullet in radians.
     */
    private double THETA;

    /**
     * The x-coordinate of the bullet.
     */
    private int x;

    /**
     * The y-coordinate of the bullet.
     */
    private int y;

    /**
     * The width of the bullet.
     */
    private int width;

    /**
     * The height of the bullet.
     */
    private int height;

    /**
     * The hitbox used for collision detection.
     */
    private Rectangle hitbox;

    /**
     * The "speed" of the bullet.
     */
    private int DELTA = 4;

    /**
     * Whether the bullet should stop updating because it was destroyed.
     */
    private boolean stopExisting = false;

    /**
     * The amount of damage this bullet inflicts on the player.
     */
    private int damage = 25;
    
    /**
     * Constructs a new Bullet.
     *
     * @param lggui  the game GUI
     * @param theta  the direction (in radians) the bullet will travel
     * @param x      initial x-coordinate
     * @param y      initial y-coordinate
     * @param width  width of the bullet
     * @param height height of the bullet
     */
    public Bullet(LevelGameplayGUI lggui, double theta, int x, int y, int width, int height) {
        
        this.lggui = lggui;
        this.lgController = lggui.getController();
        
        this.THETA = theta;
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        hitbox = new Rectangle(x, y, width, height);
        
    }
    
    /**
     * Returns the damage this bullet inflicts.
     *
     * @return damage value
     */
    public int getDamage() {
        return damage;
    }
    
    /**
     * Returns the hitbox of this bullet.
     *
     * @return hitbox rectangle
     */
    public Rectangle getHitbox() {
        return hitbox;
    }
    
    /**
     * Draws the bullet on the screen.
     * The bullet is rotated according to its direction.
     *
     * @param gtd the graphics context
     */
    public void draw(Graphics2D gtd) {

        if (stopExisting) return;

        Graphics2D gg = (Graphics2D) gtd.create();

        int centerX = x + width / 2;
        int centerY = y + height / 2;
        gg.translate(centerX, centerY);

        gg.rotate(THETA + Math.PI / 2);

        gg.setColor(Color.BLACK);
        gg.drawRect(-width / 2, -height / 2, width, height);

        gg.setColor(Color.ORANGE);
        gg.fillRect(-width / 2 + 1, -height / 2 + 1, width - 2, height - 2);

        gg.dispose();
        
    }

    /**
     * Spawns the bullet at the given coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    @Override
    public void spawn(int x, int y) {
        this.x = (Integer)(x);
        this.y = (Integer)(y);
    }
    
    /**
     * Updates the state of the bullet. 
     * Pretends to move the bullet and checks for collisions
     * with obstacles and the player’s umbrella. 
     * Destroys itself if a collision is detected.
     */
    @Override
    public void updateState() {
        
        if (stopExisting) return;
        
        int newX = x + (int)(DELTA * Math.cos(THETA));
        int newY = y + (int)(DELTA * Math.sin(THETA));
        
        for (Obstacle obs: lgController.getObstacles()) {
            if (obs.getHitbox().intersects(hitbox)) {
                destroy();
                return;
            }
        }
        
        Rectangle proximity = new Rectangle(
            x + width/2,
            y + height/2,
            8,
            8
        );
        
        if (lggui.getController().getPlayer().getUmbrella().getHitbox().intersects(proximity)) {
            destroy();
            return;
        }
        
        this.x = newX;
        this.y = newY;
        
        hitbox.x = this.x;
        hitbox.y = this.y;
        
    }
    
    /**
     * Destroys the bullet. 
     * Removes it from the game and stopping its updates.
     */
    public void destroy() {
        lgController.removeEntity(this);
        stopExisting = true;
    }
           
}

