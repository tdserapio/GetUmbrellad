package getumbrellad.models.exceptions;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.views.LevelGameplayGUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Represents a pick-up-able coin in the game.
 * The coin floats up and down in a sinusoidal 
 * motion and disappears when collected.
 */
public class Coin implements Spawnable {
    
    
    /**
     * Reference to the game GUI.
     */
    private LevelGameplayGUI lgGUI;

    /**
     * The step increment used for the floating animation.
     */
    private final double STEP = 0.1;

    /**
     * The amplitude of the floating animation.
     */
    private final double AMPLITUDE = 6.0;

    /**
     * The precise y-position used in the floating animation.
     */
    private double actualY;

    /**
     * Current step in the sinusoidal animation.
     */
    private int currentStep = 0;

    /**
     * The x-coordinate of the coin.
     */
    private int x;

    /**
     * The y-coordinate of the coin.
     */
    private int y;

    /**
     * The original y-coordinate of the coin (used for the animation).
     */
    private int originalY;

    /**
     * The width of the coin.
     */
    private final int width = 20;

    /**
     * The height of the coin.
     */
    private final int height = 20;

    /**
     * Whether the coin should stop updating and not be drawn anymore.
     */
    private boolean stopExisting = false;

    /**
     * The rectangular hitbox used to detect collisions.
     */
    private Rectangle hitbox;

    /**
     * Image of the coin.
     */
    private final Image coinImg = new ImageIcon(getClass().getResource("../../resources/umbrellacoin.png")).getImage();
    
    /**
     * Constructs a coin.
     *
     * @param lggui the game panel reference
     * @param x     initial x-coordinate of the coin
     * @param y     initial y-coordinate of the coin
     */
    public Coin(LevelGameplayGUI lggui, int x, int y) {
        
        this.lgGUI = lggui;
        
        this.x = x;
        this.y = y;
        this.originalY = y;
        
        hitbox = new Rectangle(x, y, width, height);
        actualY = y;
        
    }
    
    /**
     * Returns the hitbox used for collision detection.
     *
     * @return rectangle representing the coin's hitbox
     */
    public Rectangle getHitbox() {
        return hitbox;
    }
    
    /**
     * Draws the coin image onto the screen if it has not been destroyed.
     *
     * @param gtd the graphics context
     */
    public void draw(Graphics2D gtd) {

        if (stopExisting) return;
        gtd.drawImage(coinImg, x, y, lgGUI);
        
    }

    /**
     * Spawns the coin at the specified location.
     *
     * @param x x-coordinate to spawn at
     * @param y y-coordinate to spawn at
     */
    @Override
    public void spawn(int x, int y) {
        this.x = (Integer)(x);
        this.y = (Integer)(y);
    }
    
    /**
     * Updates the coin’s vertical position to create a floating animation.
     * Uses sine function to simulate up-and-down bobbing motion.
     * Stops updating if the coin is marked as destroyed.
     */
    @Override
    public void updateState() {
        
        if (stopExisting) return;
        
        currentStep++;
        actualY = originalY + AMPLITUDE * Math.sin(currentStep * STEP);
        y = (int)(actualY);
        hitbox.y = y;
        
    }
    
    /**
     * Removes the coin from the game and stops its drawing and updating.
     */
    public void destroy() {
        lgGUI.getController().removeEntity(this);
        stopExisting = true;
    }
           
}

