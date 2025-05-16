
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
 * Represents a non-player character (NPC) that offers upgrades to the player.
 * NPCs can open a store menu when the player interacts with them.
 */
public class NPC extends Character implements Spawnable {
    
    /**
     * The level gameplay GUI.
     */
    private LevelGameplayGUI lggui;

    /**
     * The identifier of this NPC (e.g., "NPC1", "NPC2") to know what they sell.
     */
    private String name;

    /**
     * The collision box for the NPC.
     */
    private Rectangle hitbox;

    /**
     * The x-coordinate of the NPC.
     */
    private int x;

    /**
     * The y-coordinate of the NPC.
     */
    private int y;

    /**
     * The width of the NPC.
     */
    private int width;

    /**
     * The height of the NPC.
     */
    private int height;

    /**
     * Tracks whether the player has already touched this NPC to prevent spamming.
     */
    private boolean hasInteracted = false;

    /**
     * Tracks whether the player is currently overlapping with this NPC.
     */
    private boolean isOverlapping;

    /**
     * The list of upgrades available from this NPC.
     */
    private ArrayList<Upgrade> npcUpgrades = new ArrayList<>();

    /**
     * The store menu GUI that appears when interacting with the NPC.
     */
    private StoreMenuGUI smgui;

    /**
     * Reference to the player currently interacting with the NPC.
     */
    private Player currentPlayer;

    /**
     * Image for the first type of NPC.
     */
    private final Image NPCImg1 = new ImageIcon(getClass().getResource("../../resources/paperNPC.png")).getImage();

    /**
     * Image for the second type of NPC.
     */
    private final Image NPCImg2 = new ImageIcon(getClass().getResource("../../resources/rockNPC.png")).getImage();

    /**
     * Constructs an NPC.
     *
     * @param lggui         reference to the game GUI
     * @param name          identifier name of the NPC
     * @param x             initial x-coordinate
     * @param y             initial y-coordinate
     * @param width         width of the NPC
     * @param height        height of the NPC
     * @param currentPlayer the player interacting with this NPC
     */
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
    
    /**
     * Gets the name of the NPC.
     *
     * @return NPC name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Gets the hitbox of the NPC.
     *
     * @return the hitbox rectangle
     */
    public Rectangle getHitbox() {
        return this.hitbox;
    }
    
    /**
     * Tells if the player has interacted with the NPC.
     *
     * @return true if interaction occurred, false otherwise
     */
    public boolean getHasInteracted() {
        return this.hasInteracted;
    }
    
    /**
     * Sets the interaction state for the NPC.
     *
     * @param hasInteracted true if the player has interacted
     */
    public void setHasInteracted(boolean hasInteracted) {
        this.hasInteracted = hasInteracted;
    }
    
    /**
     * Returns whether the player is currently overlapping with the NPC.
     *
     * @return true if overlapping, false otherwise
     */
    public boolean getIsOverlapping() {
        return this.isOverlapping;
    }
    
    /**
     * Sets whether the player is overlapping with the NPC.
     *
     * @param isOverlapping true if overlapping
     */
    public void setIsOverlapping(boolean isOverlapping) {
        this.isOverlapping = isOverlapping;
    }
    
    /**
     * Adds an upgrade to this NPC’s shop of upgrades.
     *
     * @param upgrade the upgrade to add
     */
    public void addUpgrade(Upgrade upgrade) {
        this.npcUpgrades.add(upgrade);
    }
    
    /**
     * Gets the store menu GUI associated with this NPC.
     *
     * @return the store menu GUI
     */
    public StoreMenuGUI getStoreMenuGUI() {
        return smgui;
    }
    
    /**
     * Opens the shop of this NPC and pauses the game.
     *
     * @param lggui         the game GUI
     * @param currentPlayer the player interacting with the shop
     */
    public void openShop(LevelGameplayGUI lggui, Player currentPlayer) {
        lggui.getController().setPaused(true);
        smgui = new StoreMenuGUI(lggui, currentPlayer, this);
        smgui.setVisible(true);
        lggui.setVisible(false);
    }
    
    /**
     * Gets the list of upgrades this NPC currently offers.
     *
     * @return list of available upgrades
     */
    public ArrayList<Upgrade> getNPCUpgrades() {
        
        npcUpgrades = new ArrayList<>();
        
        for (Upgrade currUPG: Upgrade.upgrades) {
            if (!currUPG.getIsOwned() && currUPG.getShopOwner().equals(name)) {
                npcUpgrades.add(currUPG);
            }
        }
        
        return npcUpgrades;
        
    }
    
    /**
     * Spawns this NPC at the specified position.
     *
     * @param x x-coordinate to spawn at
     * @param y y-coordinate to spawn at
     */
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Draws the image for this NPC based on its name.
     *
     * @param gtd the graphics context
     */
    @Override
    public void draw(Graphics2D gtd) {
        if (name.equals("NPC1")) {
            gtd.drawImage(NPCImg1, x, y, lgGUI);
        } else if (name.equals("NPC2")) {
            gtd.drawImage(NPCImg2, x, y, lgGUI);
        }
    }
    
    @Override
    public void updateState() {}
    
}
