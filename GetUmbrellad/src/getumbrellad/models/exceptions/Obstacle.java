package getumbrellad.models.exceptions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Obstacle implements Spawnable {
    
    private int x, y, width, height;
    private Rectangle hitbox;
    
    public Obstacle(int x, int y, int width, int height) {
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        hitbox = new Rectangle(x, y, width, height);
        
    }
    
    public Rectangle getHitbox() {
        return hitbox;
    }
    
    public void draw(Graphics2D gtd) {
        gtd.setColor(Color.GRAY);
        gtd.drawRect(x, y, width, height);
        gtd.fillRect(x+1, y+1, width-2, height-2);
    }

    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void updateState() {}
           
}

