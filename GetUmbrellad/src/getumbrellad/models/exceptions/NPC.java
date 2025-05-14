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
public class NPC extends Character implements Spawnable{
    
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
        npcUpgrades = loadShopReadingJsonFile();
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
    
    public ArrayList<Upgrade> getNPCUpgrades() {
        return this.npcUpgrades;
    }
    
    public StoreMenuGUI getStoreMenuGUI() {
        return smgui;
    }
    
    public void openShop(LevelGameplayGUI lggui, Player currentPlayer) {
        lggui.getController().setPaused(true);
        smgui = new StoreMenuGUI(lggui, currentPlayer, npcUpgrades);
        /*this.smgui = smgui;
        smgui.getController().loadThisStoresJsonFile(this);
        smgui.setCanBeBought(npcUpgrades);
        smgui.revalidate();
        smgui.repaint();*/
        smgui.setVisible(true);
        lggui.setVisible(false);
    }
    
    public ArrayList<Upgrade> loadShopReadingJsonFile() {
        
        if (Upgrade.upgrades != null) {
            Upgrade.upgrades.clear();
        }

        ArrayList<Upgrade> shopUpgrades = new ArrayList<Upgrade>();
        
        // Path is relative to src/main/resources or classpath root
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("getumbrellad/resources/misc_files/stores.csv");

        if (inputStream == null) {
            System.out.println("File not found!");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip header if necessary
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                String npcName = parts[0].trim();
                String upgradeName = parts[1].trim();
                int upgradeValue = Integer.parseInt(parts[2].trim());
                String increasedStat = parts[3].trim();
                String upgradeDescription = parts[4].trim();
                boolean upgradeOwned = (Integer.parseInt(parts[5].trim()) > 0);
                int upgradeCost = Integer.parseInt(parts[6].trim());
                                
                Upgrade currUPG = new Upgrade(upgradeName, upgradeValue, increasedStat, upgradeDescription, upgradeOwned, upgradeCost);
                //Upgrade.upgrades.add(currUPG);
                

                if (this.getName().equals(npcName)) {
                    if (!this.getNPCUpgrades().contains(currUPG) && !currentPlayer.getPlayerUpgrades().contains(currUPG)) { //if current NPC and player does not have the upgrade
                        shopUpgrades.add(currUPG);
                    }
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return shopUpgrades;
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
