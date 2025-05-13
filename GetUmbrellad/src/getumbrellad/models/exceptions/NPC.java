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
 * @author bruv
 */
public class NPC extends Character implements Spawnable{
    
    private LevelGameplayGUI lggui;
    private String name;
    private Rectangle hitbox;
    private int x, y, width, height;
    private Timer NPCTimer;
    
    private final Image NPCImg = new ImageIcon(getClass().getResource("../../resources/paperNPC.png")).getImage();
    
    public NPC(LevelGameplayGUI lggui, String name, int x, int y, int width, int height) {
        super(lggui, width, height, 20);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        gtd.drawImage(NPCImg, x, y, lgGUI);
    }
    
    @Override
    public void updateState() {
    
        
    }
    
}
