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
 * Represents a Boss enemy in the game. The Boss moves horizontally toward the player,
 * is affected by gravity, and kills the player on contact. 
 * It also spawns a portal upon death.
 */
public class Boss extends Character implements Spawnable {

    /**
     * The horizontal speed of the Boss.
     */
    private double speed = 2.3;

    /**
     * The vertical velocity of the Boss.
     */
    private double velocityY = 0;

    /**
     * The gravity affecting the Boss.
     */
    private final double GRAVITY = 0.8;

    /**
     * The maximum downward speed the Boss can reach.
     */
    private final double TERMINAL_VELOCITY = 12;

    /**
     * The x-coordinate where the Boss was initially spawned.
     */
    private final int spawnPointX;

    /**
     * The y-coordinate where the Boss was initially spawned.
     */
    private final int spawnPointY;

    /**
     * The hitbox used for collision detection.
     */
    private Rectangle hitbox;

    /**
     * Status of whether the Boss is dead.
     */
    private boolean isDead = false;

    /**
     * The direction the Boss is currently moving toward (−1 for left, 1 for right).
     */
    private int direction;

    /**
     * The image used to represent the Boss.
     */
    private final Image bossImg = new ImageIcon(getClass().getResource("../../resources/boss.png")).getImage();

    /**
     * Constructs a new Boss at the specified location with behavior tied to the game timer.
     *
     * @param lggui     the game GUI for accessing controller and view
     * @param x         the x-coordinate where the Boss spawns
     * @param y         the y-coordinate where the Boss spawns
     * @param gameTimer the game's global timer used to schedule periodic Boss actions
     */
    public Boss(LevelGameplayGUI lggui, int x, int y, Timer gameTimer) {
        
        super(lggui, 76, 102, 40);
        this.spawnPointX = x;
        this.spawnPointY = y;
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, width, height);
        this.damage = 1000;
        this.direction = 0;

        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (lggui != null && lggui.getController() != null && isCollidingWithPlayer()) {
                    lggui.getController().getPlayer().deductHP(damage);
                }
            }
        }, 0, 1000);
        
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (lgGUI != null && lgGUI.getController() != null) {
                    int playerX = lgGUI.getController().getPlayer().getX();
                    direction = Integer.compare(playerX, x);
                }
            }
        }, 0, 3500);
        
    }

    /**
     * Spawns the Boss at the specified coordinates.
     *
     * @param x the x-coordinate of where to spawn
     * @param y the y-coordinate of where to spawn
     */
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the Boss onto the screen using Graphics2D
     *
     * @param gtd the graphics context to draw on
     */
    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(bossImg, x, y, lgGUI);
    }

    
    /**
     * Checks if the Boss is colliding with any obstacles based on a probe.
     *
     * @param probe the rectangle used to test for collisions
     * @return true if it collides with any objects, false otherwise
     */
    public boolean isCollidingWithObstacle(Rectangle probe) {
        for (Obstacle obs : lgGUI.getController().getObstacles()) {
            if (obs.getHitbox().intersects(probe)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the Boss is currently colliding with the player.
     *
     * @return true if the Boss collides with the player's hitbox, false otherwise
     */
    public boolean isCollidingWithPlayer() {
        Player currentPlayer = lgGUI.getController().getPlayer();
        return currentPlayer.getHitBox().intersects(hitbox);
    }

    /**
     * Updates the Boss's state: movement, collision, and portal spawning if dead.
     */
    @Override
    public void updateState() {
        
        x += (int) (speed * direction);

        velocityY += GRAVITY;
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }

        int nextY = y + (int) velocityY;
        Rectangle groundProbe = new Rectangle(x + width / 2, nextY + height, 4, 4);

        if (isCollidingWithObstacle(groundProbe)) {
            velocityY = 0;
        } else {
            y = nextY;
        }

        hitbox.x = x;
        hitbox.y = y;
        
        if (y >= 960 && !isDead) {
            isDead = true;
            lgGUI.getController().addEntity(new Portal(this.lgGUI, spawnPointX, spawnPointY));
        }
        
    }
}
