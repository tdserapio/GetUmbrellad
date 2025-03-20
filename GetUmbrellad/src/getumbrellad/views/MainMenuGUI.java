package getumbrellad.views;

import getumbrellad.controllers.MainMenuGUIController;
import java.awt.*;
import javax.swing.*;

public class MainMenuGUI extends JFrame{
    private JPanel titlePanel, buttonPanel, fillerPanel;
    private JButton playButton, aboutButton, helpButton, leaveButton;
    private JLabel titleText;
    private MainMenuGUIController controller;
    
    public MainMenuGUI(){
        super("Main Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        //this sets space between components which centers the buttons
        this.setLayout(new BorderLayout(400, 150));

        //to move the other panels more centrally
        JPanel menuFillerPanel = new JPanel();
        this.add(menuFillerPanel, BorderLayout.WEST);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        this.add(buttonPanel, BorderLayout.CENTER);

        playButton = new JButton("Play");
        buttonPanel.add(playButton);
        aboutButton = new JButton("About");
        buttonPanel.add(aboutButton);
        helpButton = new JButton("Help");
        buttonPanel.add(helpButton);
        leaveButton = new JButton("Leave");
        buttonPanel.add(leaveButton);

        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        this.add(titlePanel, BorderLayout.NORTH);

        titleText = new JLabel();
        titleText.setText("Get Umbrella'd");
        titlePanel.add(titleText);
        
        controller = new MainMenuGUIController(this, playButton, aboutButton, helpButton, leaveButton);
        playButton.addActionListener(controller);
        aboutButton.addActionListener(controller);
        helpButton.addActionListener(controller);
        leaveButton.addActionListener(controller);
        
        playButton.addMouseListener(controller);
        aboutButton.addMouseListener(controller);
        helpButton.addMouseListener(controller);
        leaveButton.addMouseListener(controller);
        
        this.setVisible(true);
        
    
    }
}
