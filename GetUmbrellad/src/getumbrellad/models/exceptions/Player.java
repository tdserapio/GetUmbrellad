package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayGUI;
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

public class Player extends Character implements Spawnable {
    
    private int money;
    protected int xMouse, yMouse, boostDelay = 0; //movement attributes
    protected boolean keyUp, keyRight, keyLeft, canJump; //movement attributes
     
    private int yJumpFactor = 15;
    private int xJumpFactor = 10;
    
    public Player(String fileName, LevelGameplayGUI lggui) throws PlayerNotFoundException {
        
        super(lggui, 40, 70, 50, 5.0, 0.5);
        
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
                this.setPosition(200, 100);
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public int getDamage() {
        return this.damage;
    }
    public int getMoney() {
        return this.money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public int getHP() {
        return this.hp;
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
    
    public ArrayList<Obstacle> collidesWith() {
        
        ArrayList<Obstacle> collidingObjects = new ArrayList<>();
        
        for (Obstacle obstacle: lgGUI.getObstacles()) {
                if (obstacle.getHitbox().intersects(hitbox)) {
                    collidingObjects.add(obstacle);
                }
        }
        
        return collidingObjects;
        
    }
    
    public boolean isColliding() {
        return !collidesWith().isEmpty();
    }
    
    public void updateState() {
        
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
                    ySpeed = -yJumpFactor;
                    xSpeed = xJumpFactor * getDirectionalComponentOf(xMouse, x, yMouse, y, true);
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
        
        double gravity = 0.5;
        if (!canJump && !keyUp && ySpeed > 0) {
            gravity = 0.072;
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
    
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
