package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayGUI;

import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Represents an umbrella used by the player character. 
 * The umbrella rotates to face the mouse pointer and can either be open or closed.
 * When open, it may be used to block objects or slow the player's descent.
 */
public class Umbrella implements Spawnable {
    
    /**
     * The player who owns this umbrella.
     */
    private final Player player;

    /**
     * The game GUI where this umbrella is rendered.
     */
    private final LevelGameplayGUI lggui;

    /**
     * The image displayed when the umbrella is open.
     */
    private final Image openImg;

    /**
     * The image displayed when the umbrella is closed.
     */
    private final Image closedImg;

    /**
     * Whether the umbrella is currently open.
     */
    private boolean isOpen;

    /**
     * The width of the umbrella image.
     */
    private int width = 70;

    /**
     * The height of the umbrella image.
     */
    private int height = 70;

    /**
     * The x-coordinate of the umbrella's center.
     */
    private int x;

    /**
     * The y-coordinate of the umbrella's center.
     */
    private int y;

    /**
     * The distance between the player and the umbrella.
     */
    private int distanceFromPlayer = 60;

    /**
     * The hitbox of the umbrella used for collision detection.
     */
    private Rectangle hitbox;

    /**
     * Constructs an umbrella.
     *
     * @param player the player who uses the umbrella
     * @param lggui  the game GUI context where the umbrella is rendered
     */
    public Umbrella(Player player, LevelGameplayGUI lggui) {
        
        this.player = player;
        this.lggui = lggui;
        this.isOpen = false;
        
        x = player.getX();
        y = player.getY();

        this.openImg = new ImageIcon(getClass().getResource("../../resources/openumbrella.png")).getImage();
        this.closedImg = new ImageIcon(getClass().getResource("../../resources/closedumbrella.png")).getImage();
        
        this.hitbox = new Rectangle(x, y, width, height);
        
    }

    /**
     * Returns whether the umbrella is currently open.
     *
     * @return true if the umbrella is open, false otherwise
     */
    public boolean isOpen() {
        return isOpen;
    }
    
    /**
     * Returns the hitbox of the umbrella.
     *
     * @return the umbrella's hitbox as a Rectangle
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * Opens the umbrella.
     */
    public void open() {
        isOpen = true;
    }

    /**
     * Closes the umbrella.
     */
    public void close() {
        isOpen = false;
    }

    /**
     * Toggles the state of the umbrella between open and closed.
     */
    public void toggle() {
        isOpen = !isOpen;
    }
    
    /**
     * Draws the umbrella to align with the player's direction toward the mouse.
     *
     * @param gtd the graphics context used for drawing
     */
    @Override
    public void draw(Graphics2D gtd) {
        Graphics2D gg = (Graphics2D) gtd.create();

        Image currentImg = isOpen ? openImg : closedImg;

        int playerCenterX = player.x + player.width / 2;
        int playerCenterY = player.y + player.height / 2;

        int mouseX = player.xMouse;
        int mouseY = player.yMouse;

        double dx = mouseX - playerCenterX;
        double dy = mouseY - playerCenterY;
        double angle = Math.atan2(dy, dx);

        x = playerCenterX + (int)(distanceFromPlayer * Math.cos(angle));
        y = playerCenterY + (int)(distanceFromPlayer * Math.sin(angle));

        gg.translate(x, y);
        gg.rotate(angle);
        gg.drawImage(currentImg, -width / 2, -height / 2, width, height, lggui);

        gg.dispose();
        
        hitbox.x = x;
        hitbox.y = y;
    }

    @Override
    public void updateState() {}

    @Override
    public void spawn(int x, int y) {}
}
