package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayPanelGUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Character {

    protected LevelGameplayPanelGUI panel;
    protected String name;
    protected double xSpeed, ySpeed, maxSpeed = 5.00, minSpeed = 0.500, theta;
    protected int x, y, height = 70, width = 40, maxHP, hp, damage;
    protected int xMouse, yMouse, boostDelay = 0; //movement attributes
    protected boolean keyUp, keyRight, keyLeft, jumpToggle; //movement attributes
    protected Rectangle hitbox;
    final protected int range = 50;
   

    protected Character(String name, LevelGameplayPanelGUI panel, int x, int y, int maxHP, int hp, int damage) {
        
        this.name = name;
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.theta = 0.0;
        this.maxHP = maxHP;
        this.hp = hp;
        this.damage = damage;
        
        this.hitbox = new Rectangle(x, y, width, height);
        
    }
   
    /**
     * Gets the character's name
     * @return the protected String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the character's x position
     * @return the protected Float x
     */
    public float getX() {
        return this.x;
    }

    /**
     * Gets the character's y position
     * @return the protected Float y
     */
    public float getY() {
        return this.y;
    }

    /**
     * Gets the character's theta looking direction
     * @return the protected Float theta
     */
    public double getTheta() {
        return this.theta;
    }

    /**
     * Gets the character's maximum HP
     * @return the protected int maxHP
     */
    public int getmaxHP() {
        return this.maxHP;
    }

    /**
     * Gets the character's current HP
     * @return the protected int hp
     */
    public int gethp() {
        return this.hp;
    }
   
    /**
     * Gets the character's damage
     * @return the protected int damage
     */
    public int getDamage() {
        return this.damage;
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
    
    public void setJumpToggle(boolean jumpToggle) {
        this.jumpToggle = jumpToggle;
    }
    
    public void setXMouse(int xMouse) {
        this.xMouse = xMouse;
    }
    
    public void setYMouse(int yMouse) {
        this.yMouse = yMouse;
    }
    
    public void set() {
        
        
        if (keyLeft && keyRight || !keyLeft && !keyRight) {
            hitbox.y++;
            if (playerIsColliding()) {
                
                if (boostDelay == 0) {
                    xSpeed *= 0.8;
                }
            }
            hitbox.y--;
            
            
        }
        else if (keyLeft && !keyRight) {
            xSpeed--;
        }
        else if (!keyLeft && keyRight) {
            xSpeed++;
        }
        
        
        //minimum speeds
        if(xSpeed > 0 && xSpeed < minSpeed) {
            xSpeed = 0;
        }
        
        if(xSpeed < 0 && xSpeed > -minSpeed) {
            xSpeed = 0;
        }
        
        
        //maximum speeds
        if(xSpeed > maxSpeed && boostDelay == 0) {
            xSpeed = maxSpeed;
        }
        
        if(xSpeed < -maxSpeed && boostDelay == 0) {
            xSpeed = -maxSpeed;
        }
        
        
        
        if (!jumpToggle) {
            keyUp = false;
        }
        
        if (keyUp) {
            jumpToggle = false;
            
            //checking if can umbrella boost
            hitbox.y++;
            if (playerIsColliding()) {
                
                int yJumpFactor = 15;
                int xJumpFactor = 10;

                
                
                if (boostDelay == 0) {
                    boostDelay = 15;
                    ySpeed = -yJumpFactor;
                    xSpeed = xJumpFactor * getDirectionalComponentOf(xMouse, x, yMouse, y, true);
                    
                }
                else {
                    boostDelay--;
                    System.out.println(boostDelay);
                }
                
            }
            hitbox.y--;
        }
        
        if (boostDelay > 0) {
            boostDelay--;
        }
        
        double gravity = 0.5;
        if (!jumpToggle && !keyUp && ySpeed > 0) {
            gravity = 0.072;
        }
        
        
        //gravity
        ySpeed += gravity;
        
        //x collision
        hitbox.x += xSpeed;
        if (playerIsColliding()) {
            hitbox.x -= xSpeed; //returns player to previous tick in terms of x
            while(!playerIsColliding()) {
                //moves player until barely colliding the x of obstacle hitbox
                hitbox.x += Math.signum(xSpeed);
            }
            hitbox.x -= Math.signum(xSpeed); //adjusts player right against obstacle
            xSpeed = 0; //stops player
            x = hitbox.x; //sets the player position
        }
        
        //y collision
        hitbox.y += ySpeed;
        if (playerIsColliding()) {
            hitbox.y -= ySpeed; //returns player to previous tick in terms of y
            while(!playerIsColliding()) {
                //moves player until barely colliding the y of obstacle hitbox
                hitbox.y += Math.signum(ySpeed);
            }
            hitbox.y -= Math.signum(ySpeed); //adjusts player right against obstacle
            ySpeed = 0; //stops player
            y = hitbox.y; //sets the player position
        }
        
        
        
        x += xSpeed;
        y += ySpeed;
        
        hitbox.x = x;
        hitbox.y = y;
        
    }
    
    
    public boolean playerIsColliding() {
        
        boolean playerIntersects = false;
        
        for (Obstacle obstacle: panel.getObstacles()) {
                if (obstacle.getHitbox().intersects(hitbox)) {
                    playerIntersects = true;
                }
        }
        
        return playerIntersects;
        
    }
    
    public void draw(Graphics2D gtd) {
        gtd.setColor(Color.BLACK);
        gtd.fillRect(x, y, width, height);
    }
   
    public double getDirectionalComponentOf(int xMouse, int x, int yMouse, int y, boolean xComponent) {
        double component = 0;
        double direction = 0;
        
        x += this.width/2;
        y += this.height/4 * 3;
        
        
        double xDistance = Math.abs(xMouse - x);
        double yDistance = Math.abs(yMouse - y);
        double xDirection = Math.signum(xMouse - x);
        double yDirection = Math.signum(yMouse - y);
        
        if (xComponent) {
            component = xDistance / (xDistance + yDistance);
            direction = xDirection;
        }
        else {
            component = yDistance / (xDistance + yDistance);
            direction = yDirection;
        }
        
        
        System.out.println(component);
        return component * direction;
    }
    
    /* comment while there isnt an exception in the files
    public void takeDamage(int damage) throws AlreadyDeadException {
        if (hp > 0) {
            hp -= damage;
        }
        else {
            throw new AlreadyDeadException("Character " + this.name + " is dead already!");
        }
    }
    */
   
    
    /* comment while there isnt an exception in the files
    public void attack(Character character) throws OutOfReachException {

        if (this.x - character.x > range && this.x - character.x < -range) {
            System.out.println(this.name + "'s attack was not in reach");
            throw new OutOfReachException();
        } else if (this.y - character.y > range && this.y - character.y < -range){
            System.out.println(this.name + "'s attack was not in reach");
            throw new OutOfReachException();
        } else {
            try {
                int dmg = character.getDamage();
                character.takeDamage(dmg);
                return;
            }
            catch (AlreadyDeadException error) {
                System.out.println(error);
            }
            
        }

    }
   */
    
    
}

