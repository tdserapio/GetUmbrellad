package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayGUI;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Portal extends Character implements Spawnable {

    private int x, y;
    private Rectangle hitbox;

    private final Image portalImg = new ImageIcon(getClass().getResource("../../resources/portal.png")).getImage();

    public Portal(LevelGameplayGUI lggui, int x, int y) {
        super(lggui, 27, 29, 0);
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(portalImg, x, y, lgGUI);
    }

    @Override
    public void updateState() {
        Player player = lgGUI.getController().getPlayer();
        if (player.getHitBox().intersects(hitbox)) {
            player.setLevel(player.getLevel() + 1);
            lgGUI.getController().refreshLevel();
        }
    }
}
