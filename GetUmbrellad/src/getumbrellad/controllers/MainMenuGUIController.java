/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getumbrellad.controllers;
import getumbrellad.views.AboutGUI;
import getumbrellad.views.LevelGameplayGUI;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;

public class MainMenuGUIController implements ActionListener, MouseListener{
    private JFrame GUI;
    private JButton playButton, aboutButton, helpButton, leaveButton;
    
    public MainMenuGUIController(JFrame GUI, JButton playButton,JButton aboutButton, JButton helpButton, JButton leaveButton){
        this.GUI = GUI;
        this.playButton = playButton;
        this.aboutButton = aboutButton;
        this.helpButton = helpButton;
        this.leaveButton = leaveButton;
    }
    
    public void openScreen(ActionEvent e){
        //add the needed constructors when they are actually made
        if(e.getSource() == playButton){
            LevelGameplayGUI lgp = new LevelGameplayGUI();
            lgp.setVisible(true);
            GUI.dispose();
        } else if(e.getSource() == aboutButton){
            AboutGUI about = new AboutGUI();
            about.setVisible(true);
            GUI.dispose();
        } else if(e.getSource() == helpButton){
            JOptionPane.showMessageDialog(null, "The HELP page is under construction.", "Under Construction", JOptionPane.INFORMATION_MESSAGE);
            GUI.dispose();
        } else if(e.getSource() == leaveButton){
            int confirm = JOptionPane.showOptionDialog(
                GUI,
                "Are you sure you want to leave?",
                "Quit the Game", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null
            );

            if (confirm == JOptionPane.YES_OPTION) {
                GUI.dispose();
            }
        } 
    }
    
    public void changeColor(MouseEvent e){
        if(e.getSource() == playButton){
            playButton.setBackground(Color.GRAY);
        } else if(e.getSource() == aboutButton){
            aboutButton.setBackground(Color.GRAY);
        } else if(e.getSource() == helpButton){
            helpButton.setBackground(Color.GRAY);
        } else if(e.getSource() == leaveButton){
            leaveButton.setBackground(Color.GRAY);
        } 
    }
    
    public void resetColor(MouseEvent e){
        if(e.getSource() == playButton){
            playButton.setBackground(new JButton().getBackground());
        } else if(e.getSource() == aboutButton){
            aboutButton.setBackground(new JButton().getBackground());
        } else if(e.getSource() == helpButton){
            helpButton.setBackground(new JButton().getBackground());
        } else if(e.getSource() == leaveButton){
            leaveButton.setBackground(new JButton().getBackground());
        } 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        openScreen(e);
    }

    @Override    
    public void mouseEntered(MouseEvent e){
        changeColor(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        resetColor(e);
    }
}
