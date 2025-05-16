package getumbrellad.models.exceptions;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.views.LevelGameplayGUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bullet implements Spawnable {
    
    private LevelGameplayGUI lggui;
    private LevelGameplayGUIController lgController;
    private double THETA;
    private int x, y, width, height;
    private Rectangle hitbox;
    private int DELTA = 4;
    private boolean stopExisting = false;
    private int damage = 25;
    
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
    
    public int getDamage() {
        return damage;
    }
    
    public Rectangle getHitbox() {
        return hitbox;
    }
    
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

    @Override
    public void spawn(int x, int y) {
        this.x = (Integer)(x);
        this.y = (Integer)(y);
    }
    
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
    
    public void destroy() {
        lgController.removeEntity(this);
        stopExisting = true;
    }
           
}

