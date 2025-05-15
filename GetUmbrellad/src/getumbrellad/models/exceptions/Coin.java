package getumbrellad.models.exceptions;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.views.LevelGameplayGUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Coin implements Spawnable {
    
    private LevelGameplayGUI lgGUI;
    
    private final double STEP = 0.1, AMPLITUDE = 6.0;
    private double actualY;
    private int currentStep = 0;
    
    private int x, y, originalY;
    private final int width = 20, height = 20;
    private boolean stopExisting = false;
    private Rectangle hitbox;
    
    private final Image coinImg = new ImageIcon(getClass().getResource("../../resources/umbrellacoin.png")).getImage();
    
    public Coin(LevelGameplayGUI lggui, int x, int y) {
        
        this.lgGUI = lggui;
        
        this.x = x;
        this.y = y;
        this.originalY = y;
        
        hitbox = new Rectangle(x, y, width, height);
        actualY = y;
        
    }
    
    public Rectangle getHitbox() {
        return hitbox;
    }
    
    public void draw(Graphics2D gtd) {

        if (stopExisting) return;
        gtd.drawImage(coinImg, x, y, lgGUI);
        
    }

    @Override
    public void spawn(int x, int y) {
        this.x = (Integer)(x);
        this.y = (Integer)(y);
    }
    
    @Override
    public void updateState() {
        
        if (stopExisting) return;
        
        currentStep++;
        actualY = originalY + AMPLITUDE * Math.sin(currentStep * STEP);
        y = (int)(actualY);
        hitbox.y = y;
        
    }
    
    public void destroy() {
        lgGUI.getController().removeEntity(this);
        stopExisting = true;
    }
           
}

