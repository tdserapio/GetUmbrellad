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

public class Player extends Character implements Spawnable {
    
    private int money;
    protected int xMouse, yMouse, boostDelay = 0; //movement attributes
    protected boolean keyUp, keyRight, keyLeft, canJump; //movement attributes
     
    private int yJumpFactor = 10;
    private int xJumpFactor = 10;
    
    private final Image playerImg = new ImageIcon(getClass().getResource("../../resources/character.png")).getImage();
    
    private ArrayList<Upgrade> playerUpgrades = new ArrayList<>();
    private int maxHPBuff = 0, hpBuff = 0, damageBuff = 0, defenseBuff = 0, floatBuff = 0, jumpBuff = 0, coinBuff = 0;
    private double floatingEffect;
    
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
                if (isFirstLine) { // Skip header if necessary
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                
                this.name = parts[0];
                this.maxHP = Integer.parseInt(parts[1].trim());
                this.hp = Integer.parseInt(parts[1].trim());
                this.damage = Integer.parseInt(parts[2].trim());
                this.money = Integer.parseInt(parts[3].trim());
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public int getMoney() {
        return this.money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public void earnMoney() {
        this.money += 1 * coinBuff;
    }
    
    public int getHP() {
        return this.hp;
    }
    
    public void deductHP(int decrement) {
        decrement -= defenseBuff;
        this.hp -= decrement;
    }
    
    public void increaseHP(int increment) {
        this.hp += increment;
    }
    
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
                writer.write("name, hp, damage, money");
                writer.newLine();

                // Write player data
                writer.write(getName() + "," +
                             getmaxHP() + "," +
                             getDamage() + "," +
                             getMoney());
                writer.newLine();
                
                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void setKeyUp(boolean keyUp) {
        this.keyUp = keyUp;
    }
    
    public void setKeyRight(boolean keyRight) {
        this.keyRight = keyRight;
    }
    
    public void setKeyLeft(boolean keyLeft) {
        this.keyLeft = keyLeft;
    }
    
    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }
    
    public void setXMouse(int xMouse) {
        this.xMouse = xMouse;
    }
    
    public void setYMouse(int yMouse) {
        this.yMouse = yMouse;
    }
    
    public ArrayList<Obstacle> obstaclesCollision() {
        
        ArrayList<Obstacle> collidingObjects = new ArrayList<>();
        
        for (Obstacle obstacle: lgGUI.getController().getObstacles()) {
            if (obstacle.getHitbox().intersects(hitbox)) {
                collidingObjects.add(obstacle);
            }
        }
        
        return collidingObjects;
        
    }
    
    public void checkAllCollisions() {
        
        ArrayList<Spawnable> currentSpawnables = lgGUI.getController().getSpawnables();
        ArrayList<Bullet> toDestroy = new ArrayList<>();
        
        for (Spawnable entity: currentSpawnables) {
            if (entity instanceof Bullet) {
                Bullet currentBullet = (Bullet)entity;
                if (currentBullet.getHitbox().intersects(hitbox)) {
                    this.deductHP(currentBullet.getDamage());
                    toDestroy.add(currentBullet);
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
            }
        }
        
        
        for (Bullet b: toDestroy) {
            b.destroy();
        }
        
    }
    
    public boolean isColliding() {
        return !obstaclesCollision().isEmpty();
    }
    
    public boolean getIsDead() {
        return isDead;
    }
    
    @Override
    public void updateState() {
        
        checkAllCollisions();
        
        if (this.getHP() <= 0) {
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
    
    public ArrayList<Upgrade> getPlayerUpgrades() {
        return playerUpgrades;
    }
    
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
        } else if (increasedStat.equals("damage")) {
            damageBuff += value;
            damage += value;
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
    
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void draw(Graphics2D gtd) {
        gtd.setColor(Color.BLACK);
        gtd.fillRect(x, y, width, height);
        gtd.drawImage(playerImg, x, y, lgGUI);
    }

}
