package getumbrellad.views;

import getumbrellad.controllers.MainMenuGUIController;
import getumbrellad.models.exceptions.Player;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStream;

public class MainMenuGUI extends JFrame {
    
    private JPanel titlePanel, buttonPanel, overallPanel;
    private JButton playButton, aboutButton, helpButton, leaveButton;
    private JLabel titleText;
    private MainMenuGUIController controller;
    
    public MainMenuGUI() {
        
        super("Main Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        
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
        overallPanel.setBorder(new EmptyBorder(50, 50, 0, 50));
        overallPanel.setLayout(new BorderLayout());
        this.add(overallPanel);
        
        // Add Title
                
        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        overallPanel.add(titlePanel, BorderLayout.NORTH);

        titleText = new JLabel();
        titleText.setText("Get Umbrella'd");
        titleText.setFont(new Font("Lexend", Font.BOLD, 50));
        titleText.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        titlePanel.add(titleText);
        
        
        // Add Buttons
        
        ArrayList<String> buttonLabels = new ArrayList<String>();
        buttonLabels.add("play");
        buttonLabels.add("about");
        buttonLabels.add("help");
        buttonLabels.add("lore");
        buttonLabels.add("exit");
        
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        
        for (String buttonLabel: buttonLabels) {
            try {

                JButton currentButton = new JButton();
                currentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                currentButton.setBorderPainted(false);
                currentButton.setFocusPainted(false);
                currentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                currentButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/" + buttonLabel + ".png"))));
                buttonPanel.add(currentButton);
                buttons.add(currentButton);
                
            } catch (IOException ioe) {
                System.out.println("Image for " + buttonLabel + " not found :(");
                
            }
        }
        
        overallPanel.add(buttonPanel, BorderLayout.CENTER);
        
        controller = new MainMenuGUIController(this, buttons.get(0), buttons.get(1), buttons.get(2), buttons.get(4), buttons.get(3));
        for (JButton currentButton: buttons) {
            currentButton.addActionListener(controller);
            currentButton.addMouseListener(controller);
        }
        
        overallPanel.setBackground(Color.WHITE);
        titlePanel.setBackground(Color.WHITE);
        buttonPanel.setBackground(Color.WHITE);
        
        this.setVisible(true);
        
    
    }
    
}
