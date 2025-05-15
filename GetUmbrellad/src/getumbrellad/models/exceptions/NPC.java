/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getumbrellad.models.exceptions;

import getumbrellad.views.InventoryGUI;
import getumbrellad.views.LevelGameplayGUI;
import getumbrellad.views.StoreMenuGUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author bruv
 */
public class NPC extends Character implements Spawnable {
    
    private LevelGameplayGUI lggui;
    private String name;
    private Rectangle hitbox;
    private int x, y, width, height;
    private boolean hasInteracted = false, isOverlapping;
    private ArrayList<Upgrade> npcUpgrades = new ArrayList<>();
    private StoreMenuGUI smgui;
    private Player currentPlayer;
    
    private final Image NPCImg = new ImageIcon(getClass().getResource("../../resources/paperNPC.png")).getImage();
    
    public NPC(LevelGameplayGUI lggui, String name, int x, int y, int width, int height, Player currentPlayer) {
        
        super(lggui, width, height, 20);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.currentPlayer = currentPlayer;
        
        hitbox = new Rectangle(x, y, width, height);
        
        getNPCUpgrades();
        
    }
    
    public String getName() {
        return this.name;
    }
    
    public Rectangle getHitbox() {
        return this.hitbox;
    }
    
    public boolean getHasInteracted() {
        return this.hasInteracted;
    }
    
    public void setHasInteracted(boolean hasInteracted) {
        this.hasInteracted = hasInteracted;
    }
    
    public boolean getIsOverlapping() {
        return this.isOverlapping;
    }
    
    public void setIsOverlapping(boolean isOverlapping) {
        this.isOverlapping = isOverlapping;
    }
    
    public void addUpgrade(Upgrade upgrade) {
        this.npcUpgrades.add(upgrade);
    }
    
    public StoreMenuGUI getStoreMenuGUI() {
        return smgui;
    }
    
    public void openShop(LevelGameplayGUI lggui, Player currentPlayer) {
        lggui.getController().setPaused(true);
        smgui = new StoreMenuGUI(lggui, currentPlayer, this);
        smgui.setVisible(true);
        lggui.setVisible(false);
    }
    
    public ArrayList<Upgrade> getNPCUpgrades() {
        
        npcUpgrades = new ArrayList<>();
        
        for (Upgrade currUPG: Upgrade.upgrades) {
            if (!currUPG.getIsOwned() && currUPG.getShopOwner().equals(name)) {
                npcUpgrades.add(currUPG);
            }
        }
        
        return npcUpgrades;
        
    }
    

    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(NPCImg, x, y, lgGUI);
    }
    
    @Override
    public void updateState() {
    
        
    }
    
}
