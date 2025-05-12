package getumbrellad.models.exceptions;

import java.awt.Graphics2D;

public interface Spawnable {
    public void spawn(int x, int y);
    public void draw(Graphics2D gtd);
    public void updateState();
}
