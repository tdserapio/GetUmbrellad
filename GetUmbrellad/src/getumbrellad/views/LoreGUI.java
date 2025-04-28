package getumbrellad.views;

import getumbrellad.controllers.LoreGUIController;
import java.awt.*;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoreGUI extends JFrame{
    
    private JPanel overallPanel;
    private JLabel titleText, subtitleText, imageText;
    private JButton menuButton;
    private ImageIcon img;
    private LoreGUIController controller;

    public LoreGUI(){
        
        super("Lore");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.getContentPane().setBackground(Color.WHITE);
        
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
        titleText.setText("Game Premise");
        titleText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleText.setFont(new Font("Lexend", Font.BOLD, 45));
        overallPanel.add(titleText);
        
        subtitleText = new JLabel();
        subtitleText.setText("<html> <div style='text-align: center;'>In a realm where the skies are eternally stormy, the \"Paperkin\" are delicate <br> beings brought to life. Made entirely of paper, they are as <br> fragile as they are graceful, vulnerable to the elements and hostile <br> forces that lurk in their world. However, each Paperkin is gifted with <br> an umbrella — their only chance at surviving their harsh conditions. <br><br><br> You follow a Paperkin, ever so adventurous, who accidentally fell through <br>  the cracks and into the deep sewers. Will they make it out alive? </div> </html>");
        subtitleText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        subtitleText.setFont(new Font("Lexend", Font.TRUETYPE_FONT, 20));
        subtitleText.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 50));
        overallPanel.add(subtitleText);
        
        try {
            
            menuButton = new JButton();
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/mainmenu.png"))));
            
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
        
        controller = new LoreGUIController(this, menuButton);
        menuButton.addActionListener(controller);
        menuButton.addMouseListener(controller);
        
        overallPanel.setBackground(Color.WHITE);
        
        this.setVisible(true);
    }
}
