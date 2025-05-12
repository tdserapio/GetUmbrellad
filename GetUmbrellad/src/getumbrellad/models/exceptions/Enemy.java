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
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

/**
 *
 * @author troy
 */
public class Enemy extends Character implements Spawnable {
    
    private LevelGameplayGUI lggui;
    private String typeOfEnemy;
    private Rectangle hitbox;
    private int x, y, width, height;
    private Timer enemyTimer;
    
    private final Image enemyImg = new ImageIcon(getClass().getResource("../../resources/rockNPC.png")).getImage();
    
    public Enemy(LevelGameplayGUI lggui, String typeOfEnemy, int x, int y, int width, int height, Timer gameTimer) {
        super(lggui, width, height, 40);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.typeOfEnemy = typeOfEnemy;
        this.enemyTimer = gameTimer;
        
        enemyTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!lggui.getController().isPaused()) {
                    double dx = lggui.getController().getPlayer().getX() - x;
                    double dy = lggui.getController().getPlayer().getY() - y;
                    double theta = Math.atan2(dy, dx);
                    lggui.getController().addEntity(new Bullet(lggui, theta, x, y, 10, 20));
                }
            }
        }, 1, 3000);
    }

    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void draw(Graphics2D gtd) {
        gtd.setColor(Color.BLACK);
        gtd.fillRect(x, y, width, height);
        gtd.drawImage(enemyImg, x, y, lgGUI);
    }
    
    @Override
    public void updateState() {}
    
}
