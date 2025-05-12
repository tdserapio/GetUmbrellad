package getumbrellad.models.exceptions;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.views.LevelGameplayGUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class Character {

    protected LevelGameplayGUI lgGUI;
    protected LevelGameplayGUIController lgController;
    protected String name;
    protected double xSpeed, ySpeed, maxSpeed, minSpeed, theta = 0.0;
    protected int x = 0, y = 0, height, width, maxHP, hp, damage;
    protected Rectangle hitbox;
    protected int range;
    protected boolean isDead = false;
        
    protected Character(LevelGameplayGUI lgGUI, int width, int height, int range) {
        
        this.lgGUI = lgGUI;
        this.width = width;
        this.height = height;
        this.lgController = lgGUI.getController();
        
        this.hitbox = new Rectangle(x, y, width, height);
        
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        
    }

    protected Character(String name, LevelGameplayGUI panel, int maxHP, int hp, int damage, int range) {
        
        this.name = name;
        this.lgGUI = panel;
        this.lgController = lgGUI.getController();
        this.theta = 0.0;
        this.maxHP = maxHP;
        this.hp = hp;
        this.damage = damage;
        this.range = range;
        
        this.hitbox = new Rectangle(x, y, width, height);
        
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitbox.x = x;
        this.hitbox.y = y;
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
