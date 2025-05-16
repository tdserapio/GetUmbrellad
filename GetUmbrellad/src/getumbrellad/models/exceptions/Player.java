package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayGUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Represents the controllable player in the game.
 * The Player can move, jump, collect items, interact with NPCs, apply upgrades,
 * and puts the data to a file. It also handles physics, collision, and animation logic.
 */
public class Player extends Character implements Spawnable {
    
    
    /**
     * The player's current coin count.
     */
    private int money;

    /**
     * The current level the player is on.
     */
    private int currentLevel;

    /**
     * Mouse and movement state used to handle direction and input.
     */
    protected int xMouse, yMouse, boostDelay = 0;

    /**
     * Checks if keys are pressed and if player can jump.
     */
    protected boolean keyUp, keyRight, keyLeft, canJump;

    /**
     * Vertical and horizontal jump factors.
     */
    private int yJumpFactor = 10;
    private int xJumpFactor = 10;

    /**
     * Image of the player sprite.
     */
    private final Image playerImg = new ImageIcon(getClass().getResource("../../resources/character.png")).getImage();

    /**
     * List of upgrades that the player owns.
     */
    private ArrayList<Upgrade> playerUpgrades = new ArrayList<>();

    /**
     * Buff values applied from upgrades.
     */
    private int maxHPBuff = 0, hpBuff = 0, defenseBuff = 0, floatBuff = 0, jumpBuff = 0, coinBuff = 1;

    /**
     * Floating effect modifier (used for gravity).
     */
    private double floatingEffect;

    /**
     * The player's umbrella instance (used for animation and effects).
     */
    private Umbrella umbrella;
    
    /**
     * Constructs a Player.
     *
     * @param name         player name
     * @param hp           initial health
     * @param damage       damage value (always 0 because pacifist)
     * @param money        starting money
     * @param currentLevel starting level
     */
    public Player(String name, int hp, int damage, int money, int currentLevel) {
        super();
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.money = money;
        this.currentLevel = currentLevel;
    }
    
    /**
     * Constructs a Player through CSV file.
     *
     * @param fileName the file name to read player data from
     * @param lggui    the game panel
     * @throws PlayerNotFoundException if file cannot be found
     */
    public Player(String fileName, LevelGameplayGUI lggui) throws PlayerNotFoundException {
        
        super(lggui, 40, 48, 50);
        
        this.minSpeed = 0.5; 
        this.maxSpeed = 5.0;
        
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("getumbrellad/resources/misc_files/" + fileName);

        if (inputStream == null) {
            throw new PlayerNotFoundException("Player file not found!");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                
                this.name = parts[0];
                this.maxHP = Integer.parseInt(parts[1].trim());
                this.hp = Integer.parseInt(parts[1].trim());
                this.damage = Integer.parseInt(parts[2].trim());
                this.money = Integer.parseInt(parts[3].trim());
                this.currentLevel = Integer.parseInt(parts[4].trim());
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        umbrella = new Umbrella(this, this.lgGUI);
        umbrella.open();
        
    }
    
    /**
     * Returns the player's current coin balance.
     *
     * @return the amount of money the player has
     */
    public int getMoney() {
        return this.money;
    }
    
    /**
     * Returns the level the player is currently on.
     *
     * @return the current level of the player
     */
    public int getLevel() {
        return this.currentLevel;
    }
    
    /**
     * Sets the player's level and saves the data to file.
     *
     * @param newLevel the new level for the player
     */
    public void setLevel(int newLevel) {
        this.currentLevel = newLevel;
        try {
            writePlayer("umbrella_boy.csv");
        } catch (PlayerNotFoundException pnfe) {
            System.out.println("BY NO MEANS SHOULD THIS HAPPEN...");
        }
    }
    
    /**
     * Updates the player's coin count and saves it to file.
     *
     * @param money the new coin value to assign to the player
     */
    public void setMoney(int money) {
        this.money = money;
        try {
            writePlayer("umbrella_boy.csv");
        } catch (PlayerNotFoundException pnfe) {
            System.out.println("BY NO MEANS SHOULD THIS HAPPEN...");
        }
    }
    
    /**
     * Increments the player's money based on the coin buff.
     */
    public void earnMoney() {
        this.money += 1 * coinBuff;
        try {
            writePlayer("umbrella_boy.csv");
        } catch (PlayerNotFoundException pnfe) {
            System.out.println("BY NO MEANS SHOULD THIS HAPPEN...");
        }
    }
    
    /**
     * Returns the player's current health point value.
     *
     * @return the player's current health points
     */
    public int getHP() {
        return this.hp;
    }
    
    /**
     * Reduces the player's health points by a specified amount, accounting for 
     * defense buffs.
     *
     * @param decrement the amount of damage to apply
     */
    public void deductHP(int decrement) {
        decrement -= defenseBuff;
        this.hp -= decrement;
        try {
            writePlayer("umbrella_boy.csv");
        } catch (PlayerNotFoundException pnfe) {
            System.out.println("BY NO MEANS SHOULD THIS HAPPEN...");
        }
    }
    
    /**
     * Increases the player's health points by the specified amount.
     *
     * @param increment the amount of health to add
     */
    public void increaseHP(int increment) {
        this.hp += increment;
        try {
            writePlayer("umbrella_boy.csv");
        } catch (PlayerNotFoundException pnfe) {
            System.out.println("BY NO MEANS SHOULD THIS HAPPEN...");
        }
    }
    
    /**
     * Returns the total upgrade added to the player's max health points.
     *
     * @return additional max health points by upgrades
     */
    public int getMaxHPBuff() {
        return this.maxHPBuff;
    }
    
    /**
     * Returns the total health points currently added from upgrades.
     *
     * @return current health point buff applied from upgrades
     */
    public int getHpBuff() {
        return this.hpBuff;
    }
    
    /**
     * Returns the amount of damage reduction from defense upgrades.
     *
     * @return defense buff value
     */
    public int getDefenseBuff() {
        return this.defenseBuff;
    }
    
    /**
     * Returns the float buff value affecting gravity and falling speed.
     *
     * @return float buff multiplier
     */
    public int getFloatBuff() {
        return this.floatBuff;
    }
    
    /**
     * Returns the jump boost value added by upgrades.
     *
     * @return additional vertical jump power
     */
    public int getJumpBuff() {
        return this.jumpBuff;
    }
    
    /**
     * Returns the player's coin multiplier from upgrades.
     *
     * @return coin reward multiplier
     */
    public int getCoinBuff() {
        return this.coinBuff;
    }
    
    /**
     * Returns the player's collision hitbox.
     *
     * @return the player's current hitbox
     */
    public Rectangle getHitBox() {
        return this.hitbox;
    }
    
    /**
     * Returns the last recorded x-coordinate of the mouse.
     *
     * @return mouse X position
     */
    public int getMouseX() {
        return xMouse;
    }
    
    /**
     * Returns the last recorded y-coordinate of the mouse.
     *
     * @return mouse Y position
     */
    public int getMouseY() {
        return yMouse;
    }
    
    /**
     * Returns the umbrella instance used by the player.
     *
     * @return the player's umbrella
     */
    public Umbrella getUmbrella() {
        return umbrella;
    }
    
    /**
     * Returns all upgrades currently owned by the player.
     *
     * @return list of owned upgrades
     */
    public ArrayList<Upgrade> getPlayerUpgrades() {
        playerUpgrades = new ArrayList<>();
        for (Upgrade currUPG: Upgrade.upgrades) {
            if (currUPG.getIsOwned()) {
                playerUpgrades.add(currUPG);
            }
        }
        return this.playerUpgrades;
    }
    
    /**
     * Writes the player's data to a file in CSV format.
     *
     * @param fileName the file to write to
     * @throws PlayerNotFoundException if the output file cannot be found
     */
    public void writePlayer(String fileName) throws PlayerNotFoundException {

        // Path is relative to src/main/resources or classpath root
        URL dirUrl = getClass().getClassLoader().getResource("getumbrellad/resources/misc_files/");
        if (dirUrl == null) {
            throw new PlayerNotFoundException("Player directory not found!");
        }

        try {
            Path outPath = Paths.get(dirUrl.toURI()).resolve(fileName);

            try (BufferedWriter writer = Files.newBufferedWriter(outPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                // Write header
                writer.write("name, hp, damage, money, current level");
                writer.newLine();

                // Write player data
                writer.write(getName() + "," +
                             getHP() + "," +
                             getDamage() + "," +
                             getMoney() + "," + 
                             getLevel());
                writer.newLine();
                
                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Sets whether the up key is pressed.
     *
     * @param keyUp true if the key is pressed, false otherwise
     */
    public void setKeyUp(boolean keyUp) {
        this.keyUp = keyUp;
    }
    
    /**
     * Sets whether the right key is pressed.
     *
     * @param keyRight true if the key is pressed, false otherwise
     */
    public void setKeyRight(boolean keyRight) {
        this.keyRight = keyRight;
    }
    
    /**
     * Sets whether the left key is pressed.
     *
     * @param keyLeft true if the key is pressed, false otherwise
     */
    public void setKeyLeft(boolean keyLeft) {
        this.keyLeft = keyLeft;
    }
    
    /**
     * Sets whether the player is allowed to jump.
     *
     * @param canJump true if the player is allowed to jump
     */
    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }
    
    /**
     * Updates the recorded x-coordinate of the mouse.
     *
     * @param xMouse the current x position of the mouse
     */
    public void setXMouse(int xMouse) {
        this.xMouse = xMouse;
    }
    
    /**
     * Updates the recorded y-coordinate of the mouse.
     *
     * @param yMouse the current y position of the mouse
     */
    public void setYMouse(int yMouse) {
        this.yMouse = yMouse;
    }
    
    /**
     * Checks collision with all obstacles and returns a list of collisions.
     *
     * @return list of colliding obstacles
     */
    public ArrayList<Obstacle> obstaclesCollision() {
        
        ArrayList<Obstacle> collidingObjects = new ArrayList<>();
        
        for (Obstacle obstacle: lgGUI.getController().getObstacles()) {
            if (obstacle.getHitbox().intersects(hitbox)) {
                collidingObjects.add(obstacle);
            }
        }
        
        return collidingObjects;
        
    }
    
    /**
     * Checks for collisions with all entities with Player: bullets, coins, NPCs.
     * Applies the necessary consequences (damage, money, shop interaction).
     */
    public void checkAllCollisions() {
        
        ArrayList<Spawnable> currentSpawnables = lgGUI.getController().getSpawnables();
        
        ArrayList<Bullet> toDestroyBullet = new ArrayList<>();
        ArrayList<Coin> toDestroyCoin = new ArrayList<>();
        
        for (Spawnable entity: currentSpawnables) {
            if (entity instanceof Bullet) {
                Bullet currentBullet = (Bullet)entity;
                if (currentBullet.getHitbox().intersects(hitbox)) {
                    this.deductHP(currentBullet.getDamage());
                    toDestroyBullet.add(currentBullet);
                }
            } else if (entity instanceof NPC) {  
                NPC currentNPC = (NPC)entity;
                
                boolean isColliding = currentNPC.getHitbox().intersects(hitbox);
                    if (isColliding && !currentNPC.getHasInteracted()) {
                        currentNPC.setIsOverlapping(true);
                        currentNPC.setHasInteracted(true);
                        int confirm = JOptionPane.showOptionDialog(
                        this.lgGUI,
                        "Do you wish to buy from me?",
                        currentNPC.getName(), JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null
                    );
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        currentNPC.openShop(this.lgGUI, this);
                    }
                } else if (!isColliding && currentNPC.getIsOverlapping()){
                    currentNPC.setIsOverlapping(false);
                    currentNPC.setHasInteracted(false);
                }
            } else if (entity instanceof Coin) {
                Coin currentCoin = (Coin)entity;
                if (currentCoin.getHitbox().intersects(hitbox)) {
                    this.earnMoney();
                    toDestroyCoin.add(currentCoin);
                }
            }
        }
        
        
        for (Bullet b: toDestroyBullet) b.destroy();
        for (Coin c: toDestroyCoin) c.destroy();
        
    }
    
    /**
     * Checks if player collides with any obstacle. 
     *
     * @return True if the Player collides with anything otherwise False
     */
    public boolean isColliding() {
        return !obstaclesCollision().isEmpty();
    }
    
    /**
     * Checks if the player is alive or dead. 
     *
     * @return True if the Player is dead otherwise False
     */
    public boolean getIsDead() {
        return isDead;
    }
    
    
    /**
     * Updates the player's physics, input responses, and state each frame.
     * Handles jumping, collision resolution, gravity, and buff effects.
     */
    @Override
    public void updateState() {
        
        checkAllCollisions();
        
        if (this.getHP() <= 0 || this.y >= 1000) {
            this.isDead = true;
            return;
        }
        
        hitbox.y++;
        if (isColliding()) {
            if (boostDelay == 0) {
                xSpeed *= 0.8;
            }
        }
        hitbox.y--;
        
        if(xSpeed > 0 && xSpeed < minSpeed) {
            xSpeed = 0;
        }
        
        if(xSpeed < 0 && xSpeed > -minSpeed) {
            xSpeed = 0;
        }
        
        if(xSpeed > maxSpeed && boostDelay == 0) {
            xSpeed = maxSpeed;
        }
        
        if(xSpeed < -maxSpeed && boostDelay == 0) {
            xSpeed = -maxSpeed;
        }
        
        if (!canJump) {
            keyUp = false;
        }
        
        if (ySpeed < 0) {
            umbrella.close();
        } else {
            umbrella.open();
        }
        
        if (keyUp) {
            
            canJump = false;
            hitbox.y++;
            
            if (isColliding()) {
                
                if (boostDelay == 0) {
                    boostDelay = 15;
                    if (getDirectionalComponentOf(xMouse, x, yMouse, y, false) < 0) {
                        ySpeed = -yJumpFactor - jumpBuff;
                        xSpeed = xJumpFactor * getDirectionalComponentOf(xMouse, x, yMouse, y, true);
                    }
                }
                else {
                    boostDelay--;
                }
                
            }
            
            hitbox.y--;
            
        }
        
        if (boostDelay > 0) {
            boostDelay--;
        }
        
        double gravity = 0.5 -  floatingEffect;
        if (!canJump && !keyUp && ySpeed > 0) {
            gravity = 0.072 - floatingEffect;
        }
        
        //gravity
        ySpeed += gravity;
        
        //x collision
        hitbox.x += xSpeed;
        if (isColliding()) {
            hitbox.x -= xSpeed; //returns player to previous tick in terms of x
            while(!isColliding()) {
                //moves player until barely colliding the x of obstacle hitbox
                hitbox.x += Math.signum(xSpeed);
            }
            hitbox.x -= Math.signum(xSpeed); //adjusts player right against obstacle
            xSpeed = 0; //stops player
            x = hitbox.x; //sets the player position
        }
        
        //y collision
        hitbox.y += ySpeed;
        if (isColliding()) {
            hitbox.y -= ySpeed; //returns player to previous tick in terms of y
            while(!isColliding()) {
                //moves player until barely colliding the y of obstacle hitbox
                hitbox.y += Math.signum(ySpeed);
            }
            hitbox.y -= Math.signum(ySpeed); //adjusts player right against obstacle
            ySpeed = 0; //stops player
            y = hitbox.y; //sets the player position
            canJump = true;
        }
        
        x += xSpeed;
        y += ySpeed;
        
        hitbox.x = x;
        hitbox.y = y;
        
    }
    
    /**
     * Applies an upgrade's effect to the player.
     *
     * @param upgrade the upgrade to apply
     */
    public void applyUpgrade(Upgrade upgrade) {
        
        int value = upgrade.getValue();
        String increasedStat = upgrade.getIncreasedStat();
        
        if (increasedStat.equals("maxHP")) {
            maxHPBuff += value;
            maxHP += value;
            hpBuff += value;
            hp += value;
        } else if (increasedStat.equals("hp")) {
            hpBuff += value;
            hp += value;
        } else if (increasedStat.equals("defense")) {
            defenseBuff += value;
        } else if (increasedStat.equals("float")) {
            floatBuff += value;
            floatingEffect = floatBuff * 0.01;
        } else if (increasedStat.equals("jump")) {
            jumpBuff += value;
        } else if (increasedStat.equals("coin")) {
            coinBuff *= value;
        } else {
            System.out.println("Oops! The upgrade was useless.");
        }
        
    }
    
    /**
     * Spawns the player at a specified position.
     *
     * @param x the x-coordinate to spawn at
     * @param y the y-coordinate to spawn at
     */
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Draws the player's character sprite on the screen.
     *
     * @param gtd the graphics context
     */
    @Override
    public void draw(Graphics2D gtd) {
        gtd.setColor(Color.BLACK);
        gtd.fillRect(x, y, width, height);
        gtd.drawImage(playerImg, x, y, lgGUI);
    }

}
