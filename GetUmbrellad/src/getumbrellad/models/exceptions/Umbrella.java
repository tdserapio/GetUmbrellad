package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayGUI;

import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Umbrella implements Spawnable {

    private final Player player;
    private final LevelGameplayGUI lggui;

    private final Image openImg;
    private final Image closedImg;

    private boolean isOpen;
    private int width = 70;
    private int height = 70;
    
    private int x, y;
    
    private int distanceFromPlayer = 60;
    private Rectangle hitbox;

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

    public boolean isOpen() {
        return isOpen;
    }
    
    public Rectangle getHitbox() {
        return hitbox;
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    public void toggle() {
        isOpen = !isOpen;
    }
    
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
