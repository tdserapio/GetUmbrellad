package getumbrellad.views;

import getumbrellad.controllers.HelpGUIController;
import javax.swing.*;
import java.awt.*;

public class HelpGUI extends JFrame{
    
    private JButton menuButton;
    private JPanel panel[];
    private JLabel a, d, space, descriptionA, descriptionD, descriptionSpace;
    private HelpGUIController controller;
    
    public HelpGUI() {
        
        super("Help");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        
        int row = 7;
        int column = 9;
        this.setLayout(new GridLayout(row, column));
        
        panel = new JPanel[row * column];
        for (int i = 0; i < row * column; i++) {
            panel[i] = new JPanel();
            this.add(panel[i]);
        }
        
        //colored panels for a, d, and space
        panel[13].setBackground(Color.gray);
        panel[21].setBackground(Color.gray);
        panel[22].setBackground(Color.gray);
        panel[23].setBackground(Color.gray);
        
        panel[39].setBackground(Color.gray);
        panel[40].setBackground(Color.gray);
        panel[41].setBackground(Color.gray);
        
        a = new JLabel();
        a.setText("A");
        a.setFont(new Font("Arial", Font.BOLD, 60));
        a.setForeground(Color.white);
        panel[21].add(a);
        
        d = new JLabel();
        d.setText("D");
        d.setFont(new Font("Arial", Font.BOLD, 60));
        d.setForeground(Color.white);
        panel[23].add(d);
        
        space = new JLabel();
        space.setText("Space");
        space.setFont(new Font("Arial", Font.BOLD, 35));
        space.setForeground(Color.white);
        panel[40].add(space);
        
        descriptionA = new JLabel();
        descriptionA.setFont(new Font("Arial", Font.BOLD, 50));
        descriptionA.setText("<<");
        panel[20].add(descriptionA);
        
        descriptionD = new JLabel();
        descriptionD.setFont(new Font("Arial", Font.BOLD, 50));
        descriptionD.setText(">>");
        panel[24].add(descriptionD);
        
        descriptionSpace = new JLabel();
        descriptionSpace.setFont(new Font("Arial", Font.BOLD, 50));
        descriptionSpace.setText("/\\");
        panel[31].add(descriptionSpace);
        
        
        menuButton = new JButton("Main Menu");
        panel[58].add(menuButton, BorderLayout.SOUTH);
        controller = new HelpGUIController(this, menuButton);
        menuButton.addActionListener(controller);
        menuButton.addMouseListener(controller);
        
        
        
        this.setVisible(true);
    }
    
}
