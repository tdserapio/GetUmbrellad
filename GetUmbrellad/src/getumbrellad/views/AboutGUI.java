package getumbrellad.views;

import getumbrellad.controllers.AboutGUIController;
import java.awt.*;
import javax.swing.*;

public class AboutGUI extends JFrame{
    private JLabel titleText, subtitleText, imageText, descriptionText;
    private JButton menuButton;
    private ImageIcon img;
    private AboutGUIController controller;

    public AboutGUI(){
        super("About Us");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        titleText = new JLabel();
        titleText.setText("About Us");
        titleText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.add(titleText);
        
        subtitleText = new JLabel();
        subtitleText.setText("Tyrone Cabiao  |  Siegfrid Cajucom  |  Troy Serapio");
        subtitleText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.add(subtitleText);
        
        this.add(Box.createVerticalGlue());
        
        imageText = new JLabel();
        imageText.setText("The Skibidi Sigma Rizzlers");
        imageText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        imageText.setHorizontalTextPosition(JLabel.CENTER);
        imageText.setVerticalTextPosition(JLabel.TOP);
        this.add(imageText);                
        
        img = new ImageIcon(AboutGUI.class.getResource("../resources/200x150.png"));
        imageText.setIcon(img);
        
        descriptionText = new JLabel();
        descriptionText.setText("Through the fusion of Tyrone the weekend gamer, Sieggy the game addict, and Troy the rhythm heavener, Get Umbrella'd was born.");
        descriptionText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.add(descriptionText);
        
        this.add(Box.createVerticalGlue());
        
        menuButton = new JButton("Main Menu");
        menuButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.add(menuButton);
        
        controller = new AboutGUIController(this, menuButton);
        menuButton.addActionListener(controller);
        menuButton.addMouseListener(controller);
        
        
        this.setVisible(true);
    }
}
