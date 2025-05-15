/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author troy
 */
public class Shooter extends Character implements Spawnable {
    
    private int x, y, width, height;
    private Timer enemyTimer;
    private double thetaToPlayer;
    
    private final Image enemyImg = new ImageIcon(getClass().getResource("../../resources/shooter.png")).getImage();
    
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

    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
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
    
    @Override
    public void updateState() {
        double dx = lgGUI.getController().getPlayer().getX() - x;
        double dy = lgGUI.getController().getPlayer().getY() - y;
        thetaToPlayer = Math.atan2(dy, dx);
    }
    
}
