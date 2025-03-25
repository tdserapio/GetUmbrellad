package getumbrellad.views;

import getumbrellad.controllers.HelpGUIController;
import java.awt.*;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

public class HelpGUI extends JFrame {
    
    private JPanel overallPanel;
    private JPanel gridPanel;
    private JButton menuButton;
    private HelpGUIController controller;
    
    public HelpGUI() {
        
        super("Help & Controls");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.getContentPane().setBackground(Color.WHITE);

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream is = getClass().getResourceAsStream("../resources/Lexend.ttf");
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, is));
        } catch (Exception e) {
            System.out.println("Font load error: " + e.getMessage());
        }
        
        overallPanel = new JPanel();
        overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.PAGE_AXIS));
        overallPanel.setBorder(new EmptyBorder(30, 100, 50, 100));
        overallPanel.setBackground(Color.WHITE);
        overallPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(overallPanel);
        
        String[][] instructions = {
            {"../resources/arrowkeys.png", "Press A to move left.<br>Press D to move right."},
            {"../resources/spacebar.png", "Press SPACE to jump."},
            {"../resources/cursor.png", "Your mouse 'repels' the character whenever you jump."}
        };
        
        gridPanel = new JPanel(new GridLayout(instructions.length, 2, 20, 20));
        gridPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gridPanel.setBackground(Color.WHITE);
        
        ArrayList<JLabel> imgs = new ArrayList<>();
        
        for (int i = 0; i < instructions.length; i++) {
            
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            try {
                imageLabel.setIcon(new ImageIcon(getClass().getResource(instructions[i][0])));
            } catch (Exception e) {
                System.out.println("Error loading image " + instructions[i][0] + ": " + e.getMessage());
            }
            imgs.add(imageLabel);
            gridPanel.add(imageLabel);
            
            JLabel descLabel = new JLabel("<html>" + instructions[i][1] + "</html>");
            descLabel.setFont(new Font("Lexend", Font.PLAIN, 20));
            descLabel.setHorizontalAlignment(JLabel.LEFT);
            
            gridPanel.add(descLabel);
            
        }
        
        overallPanel.add(gridPanel);
        
        try {
            menuButton = new JButton();
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/mainmenu.png"))));
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } catch (IOException ioe) {
            menuButton = new JButton("MAIN MENU");
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        overallPanel.add(menuButton);
        
        controller = new HelpGUIController(this, menuButton, imgs);
        this.addKeyListener(controller);
        menuButton.addActionListener(controller);
        menuButton.addMouseListener(controller);
        
        this.setFocusable(true);
        this.setVisible(true);
    }
}
