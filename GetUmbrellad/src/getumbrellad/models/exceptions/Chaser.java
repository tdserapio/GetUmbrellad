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
 *
 * @author troy
 */
public class Chaser extends Character implements Spawnable {
    
    private int x, y, width, height;
    private double speed = 1.2;
    private final int chaseThreshold = 100;
    
    private Rectangle hitbox;

    private final Image chaserImg = new ImageIcon(getClass().getResource("../../resources/chaser.png")).getImage();

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
        }, 1000);
        
    }

    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

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
    
    public boolean isCollidingWithObstacle(Rectangle groundProbe) {
        
        for (Obstacle obs: lgGUI.getController().getObstacles()) {
            if (obs.getHitbox().intersects(groundProbe)) {
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
        
        int playerX = lgGUI.getController().getPlayer().getX();
        int playerY = lgGUI.getController().getPlayer().getY();

        if (Math.abs(playerY - y) <= chaseThreshold) {
            int direction = Integer.compare(playerX, x);
            int nextX = x + (int)(speed * direction);

            Rectangle futureHitbox = new Rectangle(nextX, y, width, height);
            Rectangle groundProbe = new Rectangle(
                futureHitbox.x + width / 2,
                futureHitbox.y + height,
                1,
                1
            );

            if (isCollidingWithObstacle(groundProbe)) {
                x = nextX;
            }
        }
        
    }
}
