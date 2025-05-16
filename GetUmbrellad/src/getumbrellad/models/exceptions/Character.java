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

/**
 * Represents an abstract class Character.
 * Characters have position, hitbox, health, damage, and movement parameters.
 * Subclasses include entities such as players, bosses, or enemies.
 */
public abstract class Character {
    
    
    /**
     * The game panel the character belongs to.
     */
    protected LevelGameplayGUI lgGUI;

    /**
     * The game Controller (of MVC).
     */
    protected LevelGameplayGUIController lgController;

    /**
     * The name of the character.
     */
    protected String name;

    /**
     * The character’s horizontal speed.
     */
    protected double xSpeed;

    /**
     * The character’s vertical speed.
     */
    protected double ySpeed;

    /**
     * Maximum movement speed.
     */
    protected double maxSpeed;

    /**
     * Minimum movement speed.
     */
    protected double minSpeed;

    /**
     * The angle (in radians) the character is facing.
     */
    protected double theta = 0.0;

    /**
     * The x-coordinate of the character.
     */
    protected int x = 0;

    /**
     * The y-coordinate of the character.
     */
    protected int y = 0;

    /**
     * The height of the character.
     */
    protected int height;

    /**
     * The width of the character.
     */
    protected int width;

    /**
     * The maximum health points of the character.
     */
    protected int maxHP;

    /**
     * The current health points of the character.
     */
    protected int hp;

    /**
     * The damage this character can inflict.
     */
    protected int damage;

    /**
     * The hitbox used for collision detection.
     */
    protected Rectangle hitbox;

    /**
     * The range within which this character can interact or attack.
     */
    protected int range;

    /**
     * Indicates whether the character is dead.
     */
    protected boolean isDead = false;
    
    /**
     * Default constructor.
     */
    protected Character() {}
        
    /**
     * Constructs a Character.
     *
     * @param lgGUI  the game panel reference
     * @param width  width of the character
     * @param height height of the character
     * @param range  interaction or attack range
     */
    protected Character(LevelGameplayGUI lgGUI, int width, int height, int range) {
        
        this.lgGUI = lgGUI;
        this.width = width;
        this.height = height;
        this.lgController = lgGUI.getController();
        
        this.hitbox = new Rectangle(x, y, width, height);
        
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        
    }

    /**
     * Constructs a Character with more details.
     *
     * @param name   name of the character
     * @param panel  GUI panel reference
     * @param maxHP  maximum health points
     * @param hp     current health points
     * @param damage damage dealt by this character
     * @param range  interaction or attack range
     */
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
    
    /**
     * Sets the position of the character and updates its hitbox.
     *
     * @param x new x-coordinate
     * @param y new y-coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitbox.x = x;
        this.hitbox.y = y;
    }
   
    /**
     * Gets the character's name.
     * @return the name of the character.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the character's x position.
     * @return the x-position of the character.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the character's y position.
     * @return the y-position of the character.
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Gets the character's width.
     * @return the width of the character.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets the character's height.
     * @return the height of the character.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Gets the character's theta looking direction.
     * @return the rotation of the character in radians.
     */
    public double getTheta() {
        return this.theta;
    }

    /**
     * Gets the character's maximum health points.
     * @return the maximum health points of the character.
     */
    public int getmaxHP() {
        return this.maxHP;
    }

    /**
     * Gets the character's current health points.
     * @return the health points of the character.
     */
    public int gethp() {
        return this.hp;
    }
   
    /**
     * Gets the character's damage.
     * @return the amount of damage a character inflicts.
     */
    public int getDamage() {
        return this.damage;
    }
   
    /**
     * Computes the component (x or y) of the direction vector from this character
     * to the given mouse coordinates.
     *
     * @param xMouse     the x-coordinate of the target
     * @param x          the x-origin of the character
     * @param yMouse     the y-coordinate of the target
     * @param y          the y-origin of the character
     * @param xComponent true to compute x-component, false for y-component
     * @return the directional component toward the target
     */
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
    
}
