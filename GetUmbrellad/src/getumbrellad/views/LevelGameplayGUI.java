package getumbrellad.views;

import getumbrellad.controllers.LevelGameplayGUIController;
import javax.swing.*;
import java.awt.*;

public class LevelGameplayGUI extends JFrame{
    
    private int 
        umbrellaX, umbrellaY,
        frameWidth = 900, frameHeight = 600, 
        gridRow = 20, gridColumn = 30,
        playerX = 2, playerY = 2;
    private LevelGameplayGUIController controller;
    private JPanel[] panel;
    private ImageIcon umbrellaImage, playerTorsoImage, playerLegsImage;
    private JButton menuButton, storeButton;
    
    
    public LevelGameplayGUI() {
        
        super("Level Gameplay");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        
        int grids = gridRow * gridColumn;
        this.setLayout(new GridLayout(gridRow, gridColumn));
        
        panel = new JPanel[grids];
        for (int i = 0; i < grids; i++) {
            panel[i] = new JPanel();
            this.add(panel[i]);
        }
        
        //show collectibles
        ImageIcon collectibleImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png")); 
        for (int i = 6; i < 11; i += 2) {
            panel[(3) * gridColumn + i].add(new JLabel(collectibleImage));
            //panel[(3) * gridColumn + i].setBackground(Color.yellow);
        }
        
        //show enemy
        ImageIcon enemyTorsoImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png"));
        ImageIcon enemyLegsImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png"));
        panel[(gridRow - 2) * gridColumn + 9].add(new JLabel(enemyLegsImage));
        panel[(gridRow - 3) * gridColumn + 9].add(new JLabel(enemyTorsoImage));
        //panel[(gridRow - 2) * gridColumn + 9].setBackground(Color.darkGray);
        //panel[(gridRow - 3) * gridColumn + 9].setBackground(Color.darkGray);
        
        
        //show player and umbrella
        playerTorsoImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png"));
        playerLegsImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png"));
        umbrellaImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png"));
        panel[(gridRow - playerY) * gridColumn + playerX].add(new JLabel(playerLegsImage));
        panel[(gridRow - (playerY + 1)) * gridColumn + playerX].add(new JLabel(playerTorsoImage));
        //panel[(gridRow - 3) * gridColumn + 2].add(new JLabel(umbrellaImage));
        //panel[(gridRow - 2) * gridColumn + 1].setBackground(Color.cyan);
        //panel[(gridRow - 3) * gridColumn + 1].setBackground(Color.cyan);
        //panel[(gridRow - 3) * gridColumn + 2].setBackground(Color.MAGENTA);     
       
        
        
 
        menuButton = new JButton("Menu");
        panel[0].add(menuButton);
        storeButton = new JButton("Store");
        panel[gridColumn].add(storeButton);
        
        //bullet image and label
        ImageIcon bulletImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png"));
        panel[gridColumn - 2].add(new JLabel(bulletImage));
        //panel[gridColumn - 2].setBackground(Color.red);
        int bulletCount = 0;
        JLabel bulletLabel = new JLabel(Integer.toString(bulletCount));
        panel[gridColumn - 1].add(bulletLabel);
        
        
        //coin image and label
        ImageIcon coinImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png"));
        panel[2 * gridColumn - 2].add(new JLabel(coinImage));
        //panel[2 * gridColumn - 2].setBackground(Color.blue);
        int coinCount = 0;
        JLabel coinLabel = new JLabel(Integer.toString(coinCount));
        panel[2 * gridColumn - 1].add(coinLabel);
        
        //show ground
        ImageIcon groundImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png"));
        for (int i = 0; i < gridColumn; i++) {
            panel[(gridRow - 1) * gridColumn + i].add(new JLabel(groundImage));
            //panel[(gridRow - 1) * gridColumn + i].setBackground(Color.pink);
        }
        
        //show platforms
        ImageIcon platformImage = new ImageIcon(getClass().getResource("../resources/placeHolderImage.png"));        
        for (int i = 4; i < 7; i++) {
            panel[(6) * gridColumn + i].add(new JLabel(platformImage));
            //panel[(6) * gridColumn + i].setBackground(Color.green);
        }
        
        for (int i = 6; i < 11; i++) {
            panel[(4) * gridColumn + i].add(new JLabel(platformImage));
            //panel[(4) * gridColumn + i].setBackground(Color.green);
        }
        
        
        
        controller = new LevelGameplayGUIController(this, menuButton, storeButton);
        menuButton.addActionListener(controller);
        storeButton.addActionListener(controller);
        this.addKeyListener(controller);
        this.addMouseListener(controller);
        menuButton.addMouseListener(controller);
        storeButton.addMouseListener(controller);
        this.addMouseMotionListener(controller);
        this.setFocusable(true);
        this.setVisible(true);
        
    }
    
    public int getGridRow() {
        return gridRow;
    }
    
    public int getGridColumn() {
        return gridColumn;
    }
    
    public int getPlayerX() {
        return playerX;
    }
    
    public int getPlayerY() {
        return playerY;
    }
    
    public JPanel[] getGridPanels() {
        return panel;
    }
    
    public double getGridRatioX() {
        return (double) gridColumn/frameWidth;
    }
    
    public double getGridRatioY() {
        return (double) gridRow/frameHeight;
    }
    
    
    public void setUmbrella(int x, int y) {
        
        umbrellaX = x;
        umbrellaY = y;
        //reset all grids around player before re-placing the umbrella
        panel[(gridRow - (playerY + 1)) * gridColumn + (playerX + 1)].removeAll();
        panel[(gridRow - (playerY + 2)) * gridColumn + (playerX + 1)].removeAll();
        panel[(gridRow - (playerY + 2)) * gridColumn + (playerX + 0)].removeAll();
        panel[(gridRow - (playerY + 2)) * gridColumn + (playerX - 1)].removeAll();
        panel[(gridRow - (playerY + 1)) * gridColumn + (playerX - 1)].removeAll();
        
        panel[(gridRow - (playerY + 1)) * gridColumn + (playerX + 1)].revalidate();
        panel[(gridRow - (playerY + 2)) * gridColumn + (playerX + 1)].revalidate();
        panel[(gridRow - (playerY + 2)) * gridColumn + (playerX + 0)].revalidate();
        panel[(gridRow - (playerY + 2)) * gridColumn + (playerX - 1)].revalidate();
        panel[(gridRow - (playerY + 1)) * gridColumn + (playerX - 1)].revalidate();
        
        panel[(gridRow - (playerY + 1)) * gridColumn + (playerX + 1)].repaint();
        panel[(gridRow - (playerY + 2)) * gridColumn + (playerX + 1)].repaint();
        panel[(gridRow - (playerY + 2)) * gridColumn + (playerX + 0)].repaint();
        panel[(gridRow - (playerY + 2)) * gridColumn + (playerX - 1)].repaint();
        panel[(gridRow - (playerY + 1)) * gridColumn + (playerX - 1)].repaint();
        
        panel[(gridRow - umbrellaY) * gridColumn + umbrellaX].add(new JLabel(umbrellaImage));
        panel[(gridRow - umbrellaY) * gridColumn + umbrellaX].revalidate();
        panel[(gridRow - umbrellaY) * gridColumn + umbrellaX].repaint();
        
        this.revalidate();
        this.setVisible(true);
    }
    
    
    
    
    
    public void movePlayer(int x, int y) {
        try {
            panel[(gridRow - umbrellaY) * gridColumn + umbrellaX].removeAll();
            panel[(gridRow - umbrellaY) * gridColumn + umbrellaX].revalidate();
            panel[(gridRow - umbrellaY) * gridColumn + umbrellaX].repaint();
        
            panel[(gridRow - playerY) * gridColumn + playerX].removeAll();
            panel[(gridRow - playerY) * gridColumn + playerX].revalidate();
            panel[(gridRow - playerY) * gridColumn + playerX].repaint();
            panel[(gridRow - (playerY + 1)) * gridColumn + playerX].removeAll();
            panel[(gridRow - (playerY + 1)) * gridColumn + playerX].revalidate();
            panel[(gridRow - (playerY + 1)) * gridColumn + playerX].repaint();
        
            playerX += x;
            playerY += y;
        
            panel[(gridRow - playerY) * gridColumn + playerX].add(new JLabel(playerLegsImage));
            panel[(gridRow - playerY) * gridColumn + playerX].revalidate();
            panel[(gridRow - playerY) * gridColumn + playerX].repaint();
            panel[(gridRow - (playerY + 1)) * gridColumn + playerX].add(new JLabel(playerTorsoImage));
            panel[(gridRow - (playerY + 1)) * gridColumn + playerX].revalidate();
            panel[(gridRow - (playerY + 1)) * gridColumn + playerX].repaint();
        }
        catch (Exception error) {
            System.out.println(error);
        }
        
    }
}