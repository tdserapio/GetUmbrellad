package getumbrellad.models.exceptions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Represents a obstacle/platform/wall in the game.
 * Obstacles have a position and size, are drawn as gray rectangles,
 * and are used to standing in the game and not fall into the void.
 */
public class Obstacle implements Spawnable {
    
    
    /**
     * The x-coordinate of the obstacle.
     */
    private int x;

    /**
     * The y-coordinate of the obstacle.
     */
    private int y;

    /**
     * The width of the obstacle.
     */
    private int width;

    /**
     * The height of the obstacle.
     */
    private int height;

    /**
     * The rectangular hitbox used for collision detection.
     */
    private Rectangle hitbox;

    /**
     * Constructs an obstacle.
     *
     * @param x      the x-coordinate of the obstacle
     * @param y      the y-coordinate of the obstacle
     * @param width  the width of the obstacle
     * @param height the height of the obstacle
     */
    public Obstacle(int x, int y, int width, int height) {
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        hitbox = new Rectangle(x, y, width, height);
        
    }
    
    /**
     * Returns the hitbox of the obstacle.
     *
     * @return a rectangle representing the obstacle's hitbox
     */
    public Rectangle getHitbox() {
        return hitbox;
    }
    
    /**
     * Draws the obstacle as a filled gray rectangle.
     *
     * @param gtd the graphics context to draw on
     */
    public void draw(Graphics2D gtd) {
        gtd.setColor(Color.GRAY);
        gtd.drawRect(x, y, width, height);
        gtd.fillRect(x+1, y+1, width-2, height-2);
    }

    /**
     * Spawns the obstacle at the specified coordinates.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void updateState() {}
           
}

