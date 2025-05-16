package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayGUI;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import java.util.Timer;
import java.util.TimerTask;

public class Boss extends Character implements Spawnable {

    private double speed = 2.3;
    private double velocityY = 0;
    private final double GRAVITY = 0.8;
    private final double TERMINAL_VELOCITY = 12;
    
    private final int spawnPointX, spawnPointY;
    
    private Rectangle hitbox;
    private boolean isDead = false;
    
    private int direction;

    private final Image bossImg = new ImageIcon(getClass().getResource("../../resources/boss.png")).getImage();

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

    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(bossImg, x, y, lgGUI);
    }

    public boolean isCollidingWithObstacle(Rectangle probe) {
        for (Obstacle obs : lgGUI.getController().getObstacles()) {
            if (obs.getHitbox().intersects(probe)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingWithPlayer() {
        Player currentPlayer = lgGUI.getController().getPlayer();
        return currentPlayer.getHitBox().intersects(hitbox);
    }

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
        
        if (y >= 960) {
            isDead = true;
            lgGUI.getController().addEntity(new Portal(this.lgGUI, spawnPointX, spawnPointY));
        }
        
    }
}
