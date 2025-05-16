package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayGUI;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Represents a portal in the game. 
 * A portal is a stationary object that,
 * when touched by the player, advances the game to the 
 * next level and refreshes the level gameplay GUI.
 */
public class Portal extends Character implements Spawnable {

    
    /**
     * The x-coordinate of the portal.
     */
    private int x;

    /**
     * The y-coordinate of the portal.
     */
    private int y;

    /**
     * The hitbox used for collision detection.
     */
    private Rectangle hitbox;

    /**
     * Checks if the portal has been used by the Player already.
     */
    private boolean alreadyTouched;

    /**
     * The image used to visually represent the portal.
     */
    private final Image portalImg = new ImageIcon(getClass().getResource("../../resources/portal.png")).getImage();

    /**
     * Constructs a portal.
     *
     * @param lggui the game GUI where this portal exists
     * @param x     the x-coordinate of the portal
     * @param y     the y-coordinate of the portal
     */
    public Portal(LevelGameplayGUI lggui, int x, int y) {
        super(lggui, 27, 29, 0);
        this.x = x;
        this.y = y;
        this.alreadyTouched = false;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    /**
     * Spawns the portal at the specified coordinates.
     *
     * @param x the new x-coordinate to place the portal
     * @param y the new y-coordinate to place the portal
     */
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the portal on the screen.
     *
     * @param gtd the graphics context to draw on
     */
    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(portalImg, x, y, lgGUI);
    }

    /**
     * Updates the portal state each frame. 
     * If the player collides with the portal and it hasn’t been used yet,
     * the player advances to the next level and the level is refreshed.
     */
    @Override
    public void updateState() {
        if (alreadyTouched) return;
        Player player = lgGUI.getController().getPlayer();
        if (player.getHitBox().intersects(hitbox) && !alreadyTouched) {
            alreadyTouched = true;
            player.setLevel(player.getLevel() + 1);
            lgGUI.getController().refreshLevel();
        }
    }
}
