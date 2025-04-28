/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getumbrellad.controllers;
import getumbrellad.models.exceptions.Player;
import getumbrellad.views.AboutGUI;
import getumbrellad.views.LevelGameplayGUI;
import getumbrellad.views.HelpGUI;
import getumbrellad.views.LoreGUI;
import getumbrellad.views.InventoryGUI;
import getumbrellad.views.StoreMenuGUI;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainMenuGUIController implements ActionListener, MouseListener{
    private JFrame GUI;
    private JButton playButton, aboutButton, helpButton, leaveButton, loreButton, inventoryButton, storeMenuButton;
    
    public MainMenuGUIController(JFrame GUI, JButton playButton,JButton aboutButton, JButton helpButton, JButton leaveButton, JButton loreButton, JButton inventoryButton, JButton storeMenuButton){
        this.GUI = GUI;
        this.playButton = playButton;
        this.aboutButton = aboutButton;
        this.helpButton = helpButton;
        this.leaveButton = leaveButton;
        this.loreButton = loreButton;
        this.inventoryButton = inventoryButton;
        this.storeMenuButton = storeMenuButton;
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
            HelpGUI help = new HelpGUI();
            help.setVisible(true);
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
        } else if (e.getSource() == loreButton) {
            LoreGUI lore = new LoreGUI();
            lore.setVisible(true);
            GUI.dispose();
        } else if (e.getSource() == inventoryButton) {
            InventoryGUI igui = new InventoryGUI(this.GUI);
            igui.setVisible(true);
            GUI.dispose();
        } else if (e.getSource() == storeMenuButton) {
            StoreMenuGUI smgui = new StoreMenuGUI(this.GUI);
            smgui.setVisible(true);
            GUI.dispose();
        }
    }
    
    public void changeColor(MouseEvent e){
        try {
            if(e.getSource() == playButton){
                playButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/play_darken.png"))));
            } else if(e.getSource() == aboutButton){
                aboutButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/about_darken.png"))));
            } else if(e.getSource() == helpButton){
                helpButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/help_darken.png"))));
            } else if(e.getSource() == leaveButton){
                leaveButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/exit_darken.png"))));
            }  else if(e.getSource() == loreButton){
                loreButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/lore_darken.png"))));
            } 
        } catch (IOException ioe) {
            System.out.println("Oops! Image not found.");
        }
    }
    
    public void resetColor(MouseEvent e){
        try {
            if(e.getSource() == playButton){
                playButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/play.png"))));
            } else if(e.getSource() == aboutButton){
                aboutButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/about.png"))));
            } else if(e.getSource() == helpButton){
                helpButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/help.png"))));
            } else if(e.getSource() == leaveButton){
                leaveButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/exit.png"))));
            } else if(e.getSource() == loreButton){
                loreButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/lore.png"))));
            }
        } catch (IOException ioe) {
            System.out.println("Oops! Image not found.");
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
