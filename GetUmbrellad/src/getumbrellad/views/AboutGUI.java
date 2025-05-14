package getumbrellad.views;

import getumbrellad.controllers.AboutGUIController;
import java.awt.*;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AboutGUI extends JFrame{
    
    private JPanel overallPanel;
    private JLabel titleText, subtitleText, imageText;
    private JButton menuButton;
    private ImageIcon img;
    private AboutGUIController controller;

    public AboutGUI(){
        
        super("About Us");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.getContentPane().setBackground(Color.WHITE);
        this.setResizable(false);
        
        // Import Lexend font
        
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream is = getClass().getResourceAsStream("../resources/Lexend.ttf");
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, is));
        } catch (Exception anyException) {
            System.out.println(anyException.getMessage());
            return;
        }
        
        // Create "overall" pane
                
        overallPanel = new JPanel();
        overallPanel.setBorder(new EmptyBorder(30, 0, 50, 0));
        overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.PAGE_AXIS));
        this.add(overallPanel);
        
        titleText = new JLabel();
        titleText.setText("About Us");
        titleText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleText.setFont(new Font("Lexend", Font.BOLD, 45));
        overallPanel.add(titleText);
        
        subtitleText = new JLabel();
        subtitleText.setText("Tyrone Cabiao  |  Siegfrid Cajucom  |  Troy Serapio");
        subtitleText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        subtitleText.setFont(new Font("Lexend", Font.ITALIC, 20));
        subtitleText.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        overallPanel.add(subtitleText);
        
        imageText = new JLabel();
        imageText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        imageText.setHorizontalTextPosition(JLabel.CENTER);
        imageText.setVerticalTextPosition(JLabel.TOP);
        img = new ImageIcon(AboutGUI.class.getResource("../resources/the_team.png"));
        imageText.setIcon(img);
        imageText.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        overallPanel.add(imageText); 
        
        try {
            
            menuButton = new JButton();
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/mainmenu.png"))));
            menuButton.setBackground(null);
            
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            overallPanel.add(menuButton);
            
        } catch (IOException ioe) {
            
            menuButton = new JButton("Main Menu");
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            overallPanel.add(menuButton);
            return;
            
        }
        
        controller = new AboutGUIController(this, menuButton);
        menuButton.addActionListener(controller);
        menuButton.addMouseListener(controller);
        
        overallPanel.setBackground(Color.WHITE);
        
        this.setVisible(true);
    }
}
